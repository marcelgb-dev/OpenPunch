package app.opunch.repository;

import app.opunch.model.WorkSession;
import app.opunch.model.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcWorkSessionRepository implements WorkSessionRepository {

    private final JdbcTemplate jdbc;

    // RowMapper template for the view_work_sessions SQL view
    private final RowMapper<WorkSession> workSessionViewRowMapper = (rs, rowNum) -> {

        // Creates a WorkSession object and fills the data
        WorkSession ws = new WorkSession();
        ws.setId(rs.getInt("work_session_id"));
        ws.setUserId(rs.getInt("user_id"));
        ws.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());

        Timestamp endTimeStamp = rs.getTimestamp("end_time");
        if (endTimeStamp != null)
            ws.setEndTime(endTimeStamp.toLocalDateTime());

        ws.setDurationMinutes(rs.getInt("duration_minutes"));

        // Creates a User object with basic data to display paired with the Work Session
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setGroupId(rs.getInt("group_id"));
        user.setRole(rs.getInt("role"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));

        // Saves the User object in the WorkSession object
        ws.setUser(user);
        // Returns the WorkSession object
        return ws;
    };

    // Constructor
    public JdbcWorkSessionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Selects all work sessions + basic information from their users
    @Override
    public List<WorkSession> findAll() {
        String sql = "SELECT * FROM view_work_sessions ORDER BY start_time DESC";

        return jdbc.query(sql, workSessionViewRowMapper);
    }

    // Selects all work sessions + basic information from a specific user by user_id
    @Override
    public List<WorkSession> findAllByUser(Integer userId) {
        String sql = """
                    SELECT * FROM view_work_sessions 
                    WHERE user_id = ? 
                    ORDER BY start_time DESC
                    """;

        return jdbc.query(sql, workSessionViewRowMapper, userId);
    }

    @Override
    public Optional<WorkSession> findOpenSession(Integer userId) {
        String sql = """
                    SELECT * FROM view_work_sessions 
                    WHERE user_id = ? AND end_time IS NULL
                    ORDER BY start_time DESC
                    LIMIT 1
                    """;

        List<WorkSession> results = jdbc.query(sql, workSessionViewRowMapper, userId);
        return returnOptional(results);
    }

    // Creates a new work session in the DB
    @Override
    public void save(WorkSession ws) {
        String sql = """
                    INSERT INTO work_sessions 
                    (user_id, start_log_id, end_log_id, start_time, end_time, duration_minutes) 
                    VALUES (?, ?, ?, ?, ?, ?)
                    """;
        jdbc.update(
                sql, ws.getUserId(),
                ws.getStartLogId(),
                ws.getEndLogId(),
                ws.getStartTime(),
                ws.getEndTime(),
                ws.getDurationMinutes());
    }

    @Override
    public void closeSession(Integer workSessionId, Integer endLogId, LocalDateTime endTime, Integer durationMinutes) {
        String sql = """
                UPDATE work_sessions
                SET end_log_id = ?,
                    end_time = ?,
                    duration_minutes = ?
                WHERE id = ?
                """;

        jdbc.update(sql, endLogId, endTime, durationMinutes, workSessionId);
    }

}
