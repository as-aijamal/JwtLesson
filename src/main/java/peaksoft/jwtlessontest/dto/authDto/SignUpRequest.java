package peaksoft.jwtlessontest.dto.authDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import peaksoft.jwtlessontest.enums.Role;

@Builder
@AllArgsConstructor
@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
