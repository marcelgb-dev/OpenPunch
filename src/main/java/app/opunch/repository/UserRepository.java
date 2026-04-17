package app.opunch.repository;

import app.opunch.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();
    List<User> findAllByGroup(Integer groupId);

    Optional<User> findById(Integer id);

    void save(User user);

}
