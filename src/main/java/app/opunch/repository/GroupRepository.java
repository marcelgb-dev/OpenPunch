package app.opunch.repository;

import app.opunch.model.Group;
import java.util.List;

public interface GroupRepository extends BaseRepository {

    List<Group> findAll();
    Group findById(Integer id);
    void save(Group group);
}
