package org.projectAsymmetricKeys.auth.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String password;

    private String confirmPassword;

}
