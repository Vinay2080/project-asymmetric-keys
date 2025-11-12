package org.projectAsymmetricKeys.auth;

import org.projectAsymmetricKeys.auth.request.AuthenticationRequest;
import org.projectAsymmetricKeys.auth.request.RefreshTokenRequest;
import org.projectAsymmetricKeys.auth.request.RegistrationRequest;
import org.projectAsymmetricKeys.auth.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse login(final AuthenticationRequest request);

    void register(final RegistrationRequest request);

    AuthenticationResponse refreshToken(final RefreshTokenRequest request);

}
