package app.opunch.repository;

import app.opunch.model.PunchLog;
import app.opunch.model.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbc;

    // RowMapper template to use with the "User Status View" queries
    private final RowMapper<User> userStatusViewRowMapper = (rs, rowNum) -> {

        // Create the User object and its data
        User u = new User();
        u.setId(rs.getInt("user_id"));
        u.setGroupId(rs.getInt("group_id"));
        u.setQrToken(rs.getString("qr_token"));
        u.setName(rs.getString("name"));
        u.setSurname(rs.getString("surname"));
        u.setRole(rs.getInt("role"));
        int lastLogId = rs.getInt("last_log_id");

        // If there's an last log for the user, extract its data and save it as a PunchLog object
        if (lastLogId > 0) {
            PunchLog l = new PunchLog();
            l.setId(lastLogId);
            l.setUserId(rs.getInt("user_id"));
            l.setLogTime(rs.getTimestamp("last_log_time").toLocalDateTime());
            l.setEvent(rs.getString("last_log_event"));

            u.setLastLog(l);
        }

        // Returns the User object
        return u;
    };

    private final RowMapper<User> loginRowMapper = (rs, rowNum) -> {
        User u = new User();

        u.setId(rs.getInt("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setRole(rs.getInt("role"));

        return u;
    };

    // Constructor
    public JdbcUserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Select the status data and last log from all users
    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM view_user_status";
        return jdbc.query(sql, userStatusViewRowMapper);
    }

    // Select the status data and last log from all active users
    @Override
    public List<User> findAllActive() {
        String sql = "SELECT * FROM view_user_status WHERE last_log_event = 'IN'";

        return jdbc.query(sql, userStatusViewRowMapper);
    }

    // Select the status data and last log from all users belonging to a specific group
    @Override
    public List<User> findAllByGroup(Integer groupId) {
        String sql = "SELECT * FROM view_user_status WHERE group_id = ?";

        return jdbc.query(sql, userStatusViewRowMapper, groupId);
    }

    // Select the status data and last log from a specific user by their id
    @Override
    public Optional<User> findById(Integer id) {
        String sql = "SELECT * FROM view_user_status WHERE user_id = ?";

        List<User> results = jdbc.query(sql, userStatusViewRowMapper, id);

        return returnOptional(results);
    }

    // Select the status data and last log from a specific user by their qr_token
    @Override
    public Optional<User> findByToken(String token) {
        String sql = "SELECT * FROM view_user_status WHERE qr_token = ?";

        List<User> results = jdbc.query(sql, userStatusViewRowMapper, token);

        return returnOptional(results);
    }

    // Select the login data from a specific user by their username
    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT id, username, password, role FROM users WHERE username = ?";

        List<User> results = jdbc.query(sql, loginRowMapper, username);

        return returnOptional(results);
    }

    // Create a new user in the DB
    @Override
    public void save(User user) {
        String sql = """
                    INSERT INTO users 
                    (group_id, qr_token, username, password, role, name, surname) 
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                    """;
        jdbc.update(sql,
                user.getGroupId(),
                user.getQrToken(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getName(),
                user.getSurname()
        );
    }

}
