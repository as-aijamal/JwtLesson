package peaksoft.jwtlessontest.service.serviceImpl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.jwtlessontest.config.jwt.JwtService;
import peaksoft.jwtlessontest.dto.authDto.AuthenticationResponse;
import peaksoft.jwtlessontest.dto.authDto.SignInRequest;
import peaksoft.jwtlessontest.dto.authDto.SignUpRequest;
import peaksoft.jwtlessontest.enitity.User;
import peaksoft.jwtlessontest.repository.UserRepository;
import peaksoft.jwtlessontest.service.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EntityExistsException("User with email " + signUpRequest.getEmail() + " already exists");
        }

        User user = User.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(signUpRequest.getRole())
                .build();
        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.getUserByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with email " + signInRequest.getEmail() + " not found"));

        if (signInRequest.getEmail().isBlank()) {
            throw new BadCredentialsException("Invalid email");
        }
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword()))
            throw new BadCredentialsException("Invalid password");


        String token = jwtService.generateToken(user);


        return AuthenticationResponse
                .builder()
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }
}














