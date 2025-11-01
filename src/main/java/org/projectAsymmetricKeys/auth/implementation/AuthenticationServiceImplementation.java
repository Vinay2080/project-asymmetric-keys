package org.projectAsymmetricKeys.auth.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.projectAsymmetricKeys.auth.AuthenticationService;
import org.projectAsymmetricKeys.auth.request.AuthenticationRequest;
import org.projectAsymmetricKeys.auth.request.RefreshTokenRequest;
import org.projectAsymmetricKeys.auth.request.RegistrationRequest;
import org.projectAsymmetricKeys.auth.response.AuthenticationResponse;
import org.projectAsymmetricKeys.exception.BusinessException;
import org.projectAsymmetricKeys.exception.ErrorCode;
import org.projectAsymmetricKeys.role.Role;
import org.projectAsymmetricKeys.role.RoleRepository;
import org.projectAsymmetricKeys.security.JwtServices;
import org.projectAsymmetricKeys.user.User;
import org.projectAsymmetricKeys.user.UserRepository;
import org.projectAsymmetricKeys.user.implementation.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor

public class AuthenticationServiceImplementation implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtServices jwtServices;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse login(final AuthenticationRequest request) {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        final User user = (User) authentication.getPrincipal();
        final String token = jwtServices.generateAccessToken(user.getUsername());
        final String refreshToken = jwtServices.generateRefreshToken(user.getUsername());
        final String tokenType = "Bearer";
        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .build();

    }

    @Override
    @Transactional
    public void register(final RegistrationRequest request) {
        checkEmail(request.getEmail());
        checkPhoneNumber(request.getPhoneNumber());
        checkPasswords(request.getPassword(), request.getConfirmPassword());

        final Role userRole = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role user does not exists"));

        final List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        final User user = userMapper.toUser(request);
        user.setRoles(roles);
        log.debug("Saving user {}", user);
        userRepository.save(user);

        final List<User> users = new ArrayList<>();
        users.add(user);
        userRole.setUsers(users);

        roleRepository.save(userRole);
    }


    @Override
    public AuthenticationResponse refreshToken(final RefreshTokenRequest request) {
        final String newAccessToken = jwtServices.refreshAccessToken(request.getRefreshToken());
        final String tokenType = "Bearer";

        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType(tokenType)
                .build();

    }

    private void checkEmail(final String email) {
        final boolean emailExists = userRepository.existsByEmailIgnoreCase(email);
        if (emailExists) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void checkPhoneNumber(String phoneNumber) {
        final boolean phoneNumberExists = userRepository.existsByPhoneNumber(phoneNumber);
        if (phoneNumberExists) {
            throw new BusinessException(ErrorCode.PHONE_ALREADY_EXISTS);
        }
    }

    private void checkPasswords(String password, String confirmPassword) {
        if (!password.matches(confirmPassword)) {
            throw new BusinessException(ErrorCode.CONFIRM_PASSWORD_MISMATCH);
        }
    }
}
