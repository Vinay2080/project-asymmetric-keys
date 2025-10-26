package org.projectAsymmetricKeys.user.implementation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.projectAsymmetricKeys.auth.request.RegistrationRequest;
import org.projectAsymmetricKeys.user.User;
import org.projectAsymmetricKeys.user.request.ProfileUpdateRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    public User toUser(RegistrationRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .locked(false)
                .credentialsExpired(false)
                .emailVerified(false)
                .phoneVerified(false)
                .build();
    }

    public void mergeUserInfo(User savedUser, ProfileUpdateRequest request) {
        if (StringUtils.isNotBlank(request.getFirstName()) && !savedUser.getFirstName().equals(request.getFirstName())) {
            savedUser.setFirstName(request.getFirstName());
        }
        if (StringUtils.isNotBlank(request.getLastName()) && !savedUser.getLastName().equals(request.getLastName())) {
            savedUser.setLastName(request.getLastName());
        }
        if (request.getDateOfBirth() != null && !savedUser.getDateOfBirth().equals(request.getDateOfBirth())) {
            savedUser.setDateOfBirth(request.getDateOfBirth());
        }
    }
}
