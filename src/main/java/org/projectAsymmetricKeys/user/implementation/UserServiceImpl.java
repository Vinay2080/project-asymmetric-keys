package org.projectAsymmetricKeys.user.implementation;

import lombok.RequiredArgsConstructor;
import org.projectAsymmetricKeys.exception.BusinessException;
import org.projectAsymmetricKeys.exception.ErrorCode;
import org.projectAsymmetricKeys.user.User;
import org.projectAsymmetricKeys.user.UserRepository;
import org.projectAsymmetricKeys.user.UserService;
import org.projectAsymmetricKeys.user.request.ChangePasswordRequest;
import org.projectAsymmetricKeys.user.request.ProfileUpdateRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String userEmail) throws UsernameNotFoundException {
        return this.userRepository.findByEmailIgnoreCase(userEmail).orElseThrow(() -> new UsernameNotFoundException("user with provided username: " + userEmail));
    }

    @Override
    @Transactional
    public void updateProfileInfo(final ProfileUpdateRequest request, final String userId) {
        final User savedUser = this.userRepository.findById(userId).orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND, userId));
        this.userMapper.mergeUserInfo(savedUser, request);
//        this.userRepository.save(savedUser);
    }

    @Override
    @Transactional
    public void changePassword(final ChangePasswordRequest request, final String userId) {
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())){
            throw new BusinessException(ErrorCode.CONFIRM_PASSWORD_MISMATCH);
        }
        User user = this.userRepository.findById(userId).orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(request.getCurrentPassword(),user.getPassword())){
            throw new BusinessException(ErrorCode.CURRENT_PASSWORD_MISMATCH);
        }
        String encodedPassword = passwordEncoder.encode(request.getConfirmNewPassword());
        user.setPassword(encodedPassword);
//        this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void deactivateAccount(final String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if(!user.isEnabled()){
            throw new BusinessException(ErrorCode.NO_STATUS_CHANGE);
        }
        user.setEnabled(false);
    }

    @Override
    @Transactional
    public void reactivateAccount(final String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if(user.isEnabled()){
            throw new BusinessException(ErrorCode.NO_STATUS_CHANGE);
        }
        user.setEnabled(true);
    }

    @Override
    @Transactional
    public void deleteAccount(final String userId) {

    }

}
