package peaksoft.jwtlessontest.service.serviceImpl;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.jwtlessontest.dto.SimpleResponse;
import peaksoft.jwtlessontest.dto.groupDto.GroupRequest;
import peaksoft.jwtlessontest.dto.groupDto.GroupResponse;
import peaksoft.jwtlessontest.dto.groupDto.SearchResponse;
import peaksoft.jwtlessontest.dto.groupDto.UpdateGroupRequest;
import peaksoft.jwtlessontest.enitity.Group;
import peaksoft.jwtlessontest.enitity.Student;
import peaksoft.jwtlessontest.repository.GroupRepository;
import peaksoft.jwtlessontest.repository.StudentRepository;
import peaksoft.jwtlessontest.service.GroupService;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;


    @Override
    public SimpleResponse saveGroup(GroupRequest groupRequest) {
        Group group = new Group();
        group.setGroupName(groupRequest.groupName());
        group.setImageLink(groupRequest.imageLink());
        group.setDescription(groupRequest.description());
        groupRepository.save(group);
        return SimpleResponse
                .builder()
                .message("Success")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public List<GroupResponse> getAllGroup() {
        return groupRepository.getAllGroups();
    }

    @Override
    public GroupResponse getByIdGroup(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));

        return new GroupResponse(
                group.getId(),
                group.getGroupName(),
                group.getImageLink(),
                group.getDescription()
        );
    }

    @Override
    public SimpleResponse updateGroupById(Long id, UpdateGroupRequest updateGroupRequest) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        group.setGroupName(updateGroupRequest.groupName());
        group.setDescription(updateGroupRequest.description());
         groupRepository.save(group);
        return SimpleResponse
                .builder()
                .message("Success updated")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        groupRepository.delete(group);
        return SimpleResponse
                .builder()
                .message("Success deleted")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse assignStudentToGroup(Long studentId, Long groupId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NullPointerException(String.format("Student with id %d not found", studentId)));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %d not found", groupId)));
        student.setGroup(group);
        studentRepository.save(student);
        return SimpleResponse
                .builder()
                .message("Success assigned")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public List<SearchResponse> searchGroup(String word) {
        return groupRepository.searchGroupsByGroupName(word);
    }
}
