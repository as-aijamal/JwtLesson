package peaksoft.jwtlessontest.service;

import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import peaksoft.jwtlessontest.dto.SimpleResponse;
import peaksoft.jwtlessontest.dto.authDto.AuthenticationResponse;
import peaksoft.jwtlessontest.dto.authDto.ProfileResponse;
import peaksoft.jwtlessontest.dto.authDto.SignInRequest;
import peaksoft.jwtlessontest.dto.authDto.SignUpRequest;
import peaksoft.jwtlessontest.dto.userDto.UserRequest;

import java.security.Principal;

public interface AuthenticationService {
    AuthenticationResponse signUp(SignUpRequest signUpRequest);
    AuthenticationResponse signIn(SignInRequest signInRequest);

//    with Security Context
    ProfileResponse getProfile();

//    with Principal
    ProfileResponse getProfile(Principal principal);
    SimpleResponse updateUser (Principal principal,Long userId, UserRequest userRequest);
}
