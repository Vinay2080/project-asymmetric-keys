package org.projectAsymmetricKeys.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.projectAsymmetricKeys.user.request.ChangePasswordRequest;
import org.projectAsymmetricKeys.user.request.ProfileUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "user", description = "User API")

public class UserController {
    private final UserService userService;

    @PatchMapping("/update/profile")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateProfile(
            @RequestBody
            @Valid
            final ProfileUpdateRequest request,
            final Authentication authentication) {
        this.userService.updateProfileInfo(request, getUserId(authentication));
    }

    @PostMapping("/update/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(
            @RequestBody
            @Valid
            final ChangePasswordRequest request,
            final Authentication authentication) {
        this.userService.changePassword(request, getUserId(authentication));
    }

    @PatchMapping("/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateAccount(
            @RequestBody
            @Valid
            final Authentication authentication){
        this.userService.deactivateAccount(getUserId(authentication));
    }
    @PatchMapping("/reactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reactivateAccount(
            @RequestBody
            @Valid
            final Authentication authentication){
        this.userService.reactivateAccount(getUserId(authentication));
    }

    private String getUserId(Authentication authentication) {
        return ((User) authentication.getPrincipal()).getId();
    }
}
