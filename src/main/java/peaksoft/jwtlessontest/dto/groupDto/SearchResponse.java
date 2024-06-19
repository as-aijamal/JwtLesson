package peaksoft.jwtlessontest.dto.groupDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResponse {
    private String groupName;
    private String imageLink;
}
