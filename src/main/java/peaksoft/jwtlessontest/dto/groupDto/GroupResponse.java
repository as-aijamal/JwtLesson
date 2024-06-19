package peaksoft.jwtlessontest.dto.groupDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupResponse {
    private Long id;
    private String groupName;
    private String imageLink;
    private String description;

    public GroupResponse(Long id, String groupName, String imageLink, String description) {
        this.id = id;
        this.groupName = groupName;
        this.imageLink = imageLink;
        this.description = description;
    }
}
