package peaksoft.jwtlessontest.dto.studentDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class StudentRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate graduationDate;
}
