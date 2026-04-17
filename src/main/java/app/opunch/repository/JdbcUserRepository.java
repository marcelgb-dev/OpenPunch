package app.opunch.repository;

import app.opunch.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    private JdbcTemplate jdbc;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getInt("id"),
            rs.getInt("group_id"),
            rs.getString("qr_token"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getInt("role"),
            rs.getString("name"),
            rs.getString("surname")
    );

    public JdbcUserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findAllByGroup(Integer groupId) {
        return null;
    }

    @Override
    public Optional<User> findById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> results = jdbc.query(sql, userRowMapper, id);

        // Comprueba si la consulta ha obtenido un resultado o no para devolver el Optional adecuado
        if (results.isEmpty()) {
            return Optional.empty();
        }
        else {
            return Optional.of(results.get(0));
        }
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (group_id, username, password, role, name, surname) VALUES (?, ?, ?, ?, ?, ?)";
        jdbc.update(sql,
                user.getGroupId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getName(),
                user.getSurname()
        );
    }
}
