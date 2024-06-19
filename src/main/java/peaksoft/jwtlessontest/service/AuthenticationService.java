package peaksoft.jwtlessontest.service;

import peaksoft.jwtlessontest.dto.authDto.AuthenticationResponse;
import peaksoft.jwtlessontest.dto.authDto.SignInRequest;
import peaksoft.jwtlessontest.dto.authDto.SignUpRequest;

public interface AuthenticationService {
    AuthenticationResponse signUp(SignUpRequest signUpRequest);
    AuthenticationResponse signIn(SignInRequest signInRequest);
}
