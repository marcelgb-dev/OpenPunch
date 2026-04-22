package app.opunch.repository;

import app.opunch.model.PunchLog;
import app.opunch.model.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcLogRepository implements PunchLogRepository {

    private final JdbcTemplate jdbc;

    // RowMapper template for the view_punch_logs SQL view
    private final RowMapper<PunchLog> logViewRowMapper = (rs, rowNum) -> {

        // Creates a PunchLog object and fills the data
        PunchLog pl = new PunchLog();
        pl.setId(rs.getInt("log_id"));
        pl.setUserId(rs.getInt("user_id"));
        pl.setLogTime(rs.getTimestamp("log_time").toLocalDateTime());
        pl.setEvent(rs.getString("event"));

        // Creates a User object with basic data to display paired with the log
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setGroupId(rs.getInt("group_id"));
        user.setRole(rs.getInt("role"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));

        // Saves the User object in the PunchLog object
        pl.setUser(user);
        // Returns the PunchLog object
        return pl;
    };

    // Constructor
    public JdbcLogRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Selects all logs + basic information from their users
    @Override
    public List<PunchLog> findAll() {
        String sql = "SELECT * FROM view_punch_logs ORDER BY log_time DESC";

        return jdbc.query(sql, logViewRowMapper);
    }

    // Selects all logs + basic information from a specific user by user_id
    @Override
    public List<PunchLog> findAllByUser(Integer userId) {
        String sql = """
                    SELECT * FROM view_punch_logs 
                    WHERE user_id = ? 
                    ORDER BY log_time DESC
                    """;

        return jdbc.query(sql, logViewRowMapper, userId);
    }

    // Creates a new log in the DB
    @Override
    public void save(PunchLog log) {
        String sql = "INSERT INTO punch_logs (user_id, event) VALUES (?, ?)";
        jdbc.update(sql, log.getUserId(), log.getEvent());
    }

}
