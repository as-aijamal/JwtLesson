package peaksoft.jwtlessontest.service;


import peaksoft.jwtlessontest.dto.SimpleResponse;
import peaksoft.jwtlessontest.dto.groupDto.GroupRequest;
import peaksoft.jwtlessontest.dto.groupDto.GroupResponse;
import peaksoft.jwtlessontest.dto.groupDto.SearchResponse;
import peaksoft.jwtlessontest.dto.groupDto.UpdateGroupRequest;

import java.util.List;

public interface GroupService {
    SimpleResponse saveGroup(GroupRequest groupRequest);

    List<GroupResponse> getAllGroup();

    GroupResponse getByIdGroup(Long id);

    SimpleResponse updateGroupById(Long id, UpdateGroupRequest updateGroupRequest) ;

    SimpleResponse deleteById(Long id) ;

    SimpleResponse assignStudentToGroup(Long studentId,Long groupId);

    List<SearchResponse> searchGroup(String word);

}
