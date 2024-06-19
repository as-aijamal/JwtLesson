package peaksoft.jwtlessontest.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.jwtlessontest.dto.SimpleResponse;
import peaksoft.jwtlessontest.dto.authDto.ProfileResponse;
import peaksoft.jwtlessontest.dto.userDto.UserRequest;
import peaksoft.jwtlessontest.service.AuthenticationService;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApi {

    private final AuthenticationService authenticationService;

    //    with Security
    @GetMapping("/profile")
    public ProfileResponse getProfile() {
        return authenticationService.getProfile();
    }


    //    with Principal
    @GetMapping("/profilePrincipal")
    public ProfileResponse getProfile(Principal principal) {
        return authenticationService.getProfile();
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
    public SimpleResponse updateUser(Principal principal, @PathVariable Long userId, @RequestBody UserRequest userRequest) {
        return authenticationService.updateUser(principal, userId, userRequest);
    }
}

