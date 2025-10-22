package org.projectAsymmetricKeys.user.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProfileUpdateRequest {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
