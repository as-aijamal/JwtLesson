package peaksoft.jwtlessontest.service;



import peaksoft.jwtlessontest.dto.SimpleResponse;
import peaksoft.jwtlessontest.dto.studentDto.PaginationResponse;
import peaksoft.jwtlessontest.dto.studentDto.StudentRequest;
import peaksoft.jwtlessontest.dto.studentDto.StudentResponse;

import java.util.List;

public interface StudentService {

    SimpleResponse saveStudent(StudentRequest studentRequest);

    StudentResponse getStudentById(Long id);

    List<StudentResponse> getAllStudents();

    SimpleResponse updateStudent(Long id, StudentRequest student);

    SimpleResponse deleteStudent(Long id);

    StudentResponse getByEmail(String email);

    PaginationResponse getAllStudentsWithPagination(int currentPage, int pageSize);
}
