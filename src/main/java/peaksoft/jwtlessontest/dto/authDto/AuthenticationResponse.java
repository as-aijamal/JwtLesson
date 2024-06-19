package peaksoft.jwtlessontest.dto.authDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import peaksoft.jwtlessontest.enums.Role;
@Builder
@AllArgsConstructor
@Data
public class AuthenticationResponse {
    private String token;
    private String email;
    private Role role;
}
