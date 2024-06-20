package peaksoft.jwtlessontest.service.serviceImpl;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.jwtlessontest.config.jwt.JwtService;
import peaksoft.jwtlessontest.dto.SimpleResponse;
import peaksoft.jwtlessontest.dto.authDto.AuthenticationResponse;
import peaksoft.jwtlessontest.dto.authDto.ProfileResponse;
import peaksoft.jwtlessontest.dto.authDto.SignInRequest;
import peaksoft.jwtlessontest.dto.authDto.SignUpRequest;
import peaksoft.jwtlessontest.dto.userDto.UserRequest;
import peaksoft.jwtlessontest.enitity.User;
import peaksoft.jwtlessontest.enums.Role;
import peaksoft.jwtlessontest.exception.AlreadyExistException;
import peaksoft.jwtlessontest.exception.BadCredentialException;
import peaksoft.jwtlessontest.exception.NotFoundException;
import peaksoft.jwtlessontest.repository.UserRepository;
import peaksoft.jwtlessontest.service.AuthenticationService;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @PostConstruct
    public void initSaveAdmin() {
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("Adminov");
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("Admin123"));
        user.setRole(Role.ADMIN);
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }

    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AlreadyExistException("User with email " + signUpRequest.getEmail() + " already exists");
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
                .orElseThrow(() -> new NotFoundException(
                        "User with email " + signInRequest.getEmail() + " not found"));

        if (signInRequest.getEmail().isBlank()) {
            throw new BadCredentialException("Invalid email");
        }
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword()))
            throw new BadCredentialException("Invalid password");


        String token = jwtService.generateToken(user);


        return AuthenticationResponse
                .builder()
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }


//    with SecurityContext

    @Override
    public ProfileResponse getProfile() {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        String email = authentication.getName();

        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return ProfileResponse
                .builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    //    With Principal
    @Override
    public ProfileResponse getProfile(Principal principal) {
        String email = principal.getName();
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return ProfileResponse
                .builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public SimpleResponse updateUser(Principal principal,Long userId, UserRequest userRequest) {

//        with Security context
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();


//        with Principal
        String email = principal.getName();

        User currentUser = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        User updateUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getId() == userId) {
            updateUser.setFirstName(userRequest.firstName());
            updateUser.setEmail(userRequest.email());
            userRepository.save(updateUser);
        } else {
            return SimpleResponse
                    .builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .build();
        }


        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .build();
    }
}














