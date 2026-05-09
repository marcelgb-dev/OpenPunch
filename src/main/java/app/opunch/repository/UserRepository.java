package app.opunch.repository;

import app.opunch.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository {

    List<User> findAllFull();
    List<User> findAll();
    List<User> findAllActive();
    List<User> findByRole(Integer role);

    Optional<User> findById(Integer id);
    Optional<User> findByToken(String token);
    Optional<User> findByUsername(String username);

    void save(User user);
    void remove(Integer id);
}
