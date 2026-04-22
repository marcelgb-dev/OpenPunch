package app.opunch.repository;

import app.opunch.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository {

    List<User> findAll();
    List<User> findAllByGroup(Integer groupId);
    List<User> findAllActive();

    Optional<User> findById(Integer id);
    Optional<User> findByToken(String token);
    Optional<User> findByUsername(String username);

    void save(User user);

}
