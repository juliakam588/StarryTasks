package com.starrytasks.backend.service;

import com.starrytasks.backend.api.external.*;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse login(AuthenticationRequest request);

    UserDTO verify();
}
