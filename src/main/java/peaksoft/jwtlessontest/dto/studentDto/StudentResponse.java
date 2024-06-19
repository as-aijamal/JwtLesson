package peaksoft.jwtlessontest.dto.studentDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class StudentResponse {
    private Long id;
    private String fullName;
    private String email;
    private LocalDate createdDate;
    private LocalDate graduationDate;
    private boolean isBlocked;

    public StudentResponse(Long id, String fullName, String email, LocalDate createdDate, LocalDate graduationDate, boolean isBlocked) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.createdDate = createdDate;
        this.graduationDate = graduationDate;
        this.isBlocked = isBlocked;
    }
}
