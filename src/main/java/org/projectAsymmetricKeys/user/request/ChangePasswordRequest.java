package org.projectAsymmetricKeys.user.request;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ChangePasswordRequest  {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;

}


