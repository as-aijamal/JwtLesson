package peaksoft.jwtlessontest.dto.authDto;

import lombok.Builder;
import peaksoft.jwtlessontest.enums.Role;

@Builder
public record ProfileResponse(
        String firstName,
        String lastName,
        String email,
        Role role
) {
}
