package peaksoft.jwtlessontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.jwtlessontest.dto.groupDto.GroupResponse;
import peaksoft.jwtlessontest.dto.groupDto.SearchResponse;
import peaksoft.jwtlessontest.enitity.Group;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {

    @Query("select new peaksoft.jwtlessontest.dto.groupDto.GroupResponse(g.id,g.groupName,g.imageLink,g.description) from Group g")
    List<GroupResponse> getAllGroups();



    List<SearchResponse> searchGroupsByGroupName(String word);

}
