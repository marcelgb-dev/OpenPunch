package app.opunch.repository;

import app.opunch.model.PunchLog;
import app.opunch.model.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcLogRepository implements PunchLogRepository {

    private final JdbcTemplate jdbc;

    // Plantilla de RowMapper para usar en las queries.
    private final RowMapper<PunchLog> logRowMapper = (rs, rowNum) -> {

        // Crea un objeto PunchLog y le asigna sus campos
        PunchLog pl = new PunchLog();
        pl.setId(rs.getInt("log_id"));
        pl.setUserId(rs.getInt("user_id"));
        pl.setLogTime(rs.getTimestamp("log_time").toLocalDateTime());
        pl.setEvent(rs.getString("event"));

        // Crea un objeto User enlazado a los datos del usuario del log y le asigna los campos básicos
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setGroupId(rs.getInt("group_id"));
        user.setRole(rs.getInt("role"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));

        // Guarda el usuario en el objeto del log
        pl.setUser(user);

        // Devuelve el log
        return pl;
    };

    // Constructor
    public JdbcLogRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Selecciona todos los logs y de cada log los campos básicos de su usuario correspondiente
    @Override
    public List<PunchLog> findAll() {
        String sql =
                "SELECT l.id as log_id, l.event, l.log_time," + // Campos del log
                " u.id as user_id, u.group_id, u.role, u.name, u.surname" + // Campos del usuario
                " FROM punch_logs l " +
                " JOIN users u ON l.user_id = u.id" +
                " ORDER BY l.log_time DESC";

        return jdbc.query(sql, logRowMapper);
    }

    // Selecciona todos los logs de un usuario y de cada log los campos básicos del usuario
    @Override
    public List<PunchLog> findAllByUser(Integer userId) {
        String sql =
            "SELECT l.id as log_id, l.event, l.log_time," +
            " u.id as user_id, u.group_id, u.role, u.name, u.surname" +
            " FROM punch_logs l " +
            " JOIN users u ON l.user_id = u.id" +
            " WHERE l.user_id = ?" +
            " ORDER BY l.log_time DESC";

        return jdbc.query(sql, logRowMapper, userId);
    }

    // Crea un nuevo log en la BD
    @Override
    public void save(PunchLog log) {
        String sql = "INSERT INTO punch_logs (user_id, event) VALUES (?, ?)";
        jdbc.update(sql, log.getUserId(), log.getEvent());
    }

}
