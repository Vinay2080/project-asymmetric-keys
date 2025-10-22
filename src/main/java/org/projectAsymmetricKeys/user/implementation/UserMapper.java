package org.projectAsymmetricKeys.user.implementation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.projectAsymmetricKeys.user.User;
import org.projectAsymmetricKeys.user.request.ProfileUpdateRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
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
