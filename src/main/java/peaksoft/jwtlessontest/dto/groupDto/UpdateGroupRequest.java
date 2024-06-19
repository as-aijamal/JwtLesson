package peaksoft.jwtlessontest.dto.groupDto;

import lombok.Builder;

@Builder
public record UpdateGroupRequest(
        String groupName,
        String description
) {
}
