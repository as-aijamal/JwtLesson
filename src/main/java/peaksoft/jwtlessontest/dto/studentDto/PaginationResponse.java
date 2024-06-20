package peaksoft.jwtlessontest.dto.studentDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Getter
@Setter
public class PaginationResponse {
    private List<StudentResponse> students;
    private int currentPage;
    private int pageSize;

}
