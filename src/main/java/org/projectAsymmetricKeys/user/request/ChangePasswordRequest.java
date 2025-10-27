package org.projectAsymmetricKeys.user.request;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// todo add validations and pull request from ALI BOULI
public class ChangePasswordRequest  {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;

}


