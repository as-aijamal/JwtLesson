package peaksoft.jwtlessontest.api;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.jwtlessontest.dto.SimpleResponse;
import peaksoft.jwtlessontest.dto.studentDto.PaginationResponse;
import peaksoft.jwtlessontest.dto.studentDto.StudentRequest;
import peaksoft.jwtlessontest.dto.studentDto.StudentResponse;
import peaksoft.jwtlessontest.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentApi {
    private final StudentService studentService;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public SimpleResponse saveStudent(@RequestBody @Valid StudentRequest studentRequest) {
        return studentService.saveStudent(studentRequest);
    }


    @GetMapping("/{id}/get")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR','STUDENT')")
    StudentResponse getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }


    @GetMapping
    @PermitAll
    List<StudentResponse>getAllStudents(){
        return studentService.getAllStudents();
    }

    @PermitAll
    @GetMapping("/getEmail")
    StudentResponse getStudentByEmail(@RequestParam String email){
        return studentService.getByEmail(email);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PutMapping("/{id}")
    public SimpleResponse updateStudent(@PathVariable Long id,@RequestBody StudentRequest studentRequest){
        return studentService.updateStudent(id,studentRequest);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteStudent(@PathVariable Long id){
        return studentService.deleteStudent(id);
    }

    @GetMapping("/pagination")
    public PaginationResponse pagination (@RequestParam int currentPage,
                                          @RequestParam int pageSize){
        return studentService.getAllStudentsWithPagination(currentPage,pageSize);
    }


}
