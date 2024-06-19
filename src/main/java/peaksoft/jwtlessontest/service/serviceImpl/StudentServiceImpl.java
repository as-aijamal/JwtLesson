package peaksoft.jwtlessontest.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.jwtlessontest.dto.SimpleResponse;
import peaksoft.jwtlessontest.dto.studentDto.StudentRequest;
import peaksoft.jwtlessontest.dto.studentDto.StudentResponse;
import peaksoft.jwtlessontest.enitity.Student;
import peaksoft.jwtlessontest.enitity.User;
import peaksoft.jwtlessontest.enums.Role;
import peaksoft.jwtlessontest.repository.StudentRepository;
import peaksoft.jwtlessontest.repository.UserRepository;
import peaksoft.jwtlessontest.service.StudentService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Override
    public SimpleResponse saveStudent(StudentRequest studentRequest) {
        User user = new User();
        user.setFirstName(studentRequest.getFirstName());
        user.setLastName(studentRequest.getLastName());
        user.setEmail(studentRequest.getEmail());
        user.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        user.setRole(Role.STUDENT);
        userRepository.save(user);
        Student student = new Student();
        student.setCreatedDate(LocalDate.now());
        student.setGraduationDate(studentRequest.getGraduationDate());
        student.setBlocked(false);
        student.setUser(user);
        studentRepository.save(student);
        return new SimpleResponse(
                HttpStatus.OK,
                "Student with id " + student.getId() + " is saved"
        );
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAllStudents();
    }


    @Override
    public StudentResponse getStudentById(Long id) {
        return studentRepository.getStudentById(id).orElseThrow(
                () -> new NullPointerException(String.format("Student with id %d not found", id)));
    }


    @Override
    public SimpleResponse updateStudent(Long id, StudentRequest studentRequest) {

        Student oldStudent = studentRepository.findById(id).orElseThrow(
                () -> new NullPointerException(String.format("Student with id %d not found", id)));
        User user = userRepository.findById(oldStudent.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("not found"));
        user.setFirstName(studentRequest.getFirstName());
        user.setLastName(studentRequest.getLastName());
        user.setEmail(studentRequest.getEmail());
        user.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        userRepository.save(user);
        oldStudent.setGraduationDate(studentRequest.getGraduationDate());
        oldStudent.setUser(user);
        studentRepository.save(oldStudent);
        return new SimpleResponse(
                HttpStatus.OK,
                "Student with id " + oldStudent.getId() + " is updated"
        );
    }

    @Override
    public SimpleResponse deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student with id " + id + " not found");
        }
        studentRepository.deleteById(id);
        return  SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message( "Student with id " + id + " was deleted")
                .build();

    }

    @Override
    public StudentResponse getByEmail(String email) {
        return studentRepository.getStudentByEmail(email);
    }
}
