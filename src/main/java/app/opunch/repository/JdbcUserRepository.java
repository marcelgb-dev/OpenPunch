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

    // Plantilla de RowMapper para obtener los datos de un usuario y sus logs asociados
    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {

        // Crea el objeto usuario y guarda sus datos
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setGroupId(rs.getInt("group_id"));
        u.setQrToken(rs.getString("qr_token"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setRole(rs.getInt("role"));
        u.setName(rs.getString("name"));
        u.setSurname(rs.getString("surname"));

        int lastLogId = rs.getInt("last_id");

        // Si existe algún log para este usuario, coge sus datos y guárdalos en el objeto usuario
        if (lastLogId > 0) {
            PunchLog l = new PunchLog();
            l.setId(lastLogId);
            l.setUserId(rs.getInt("id"));
            l.setLogTime(rs.getTimestamp("last_log_time").toLocalDateTime());
            l.setEvent(rs.getString("last_event"));

            u.setLastLog(l);
        }

        // Devuelve el objeto usuario
        return u;
    };

    // Constructor
    public JdbcUserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Selecciona los datos de todos los usuarios y sus logs asociados
    @Override
    public List<User> findAll() {
        String sql =
                "SELECT" +
                    " u.*," +
                    " pl.id as last_id," +
                    " pl.log_time as last_log_time," +
                    " pl.event as last_event" +
                " FROM users u" +
                " LEFT JOIN punch_logs pl ON pl.id = (" +
                    " SELECT id FROM punch_logs " +
                    " WHERE user_id = u.id" +
                    "  ORDER BY log_time DESC" +
                    " LIMIT 1)";

        return jdbc.query(sql, userRowMapper);
    }

    // Selecciona los datos de todos los usuarios activos y sus logs asociados
    @Override
    public List<User> findAllActive() {
        String sql =
                "SELECT" +
                    " u.*," +
                    " pl.id as last_id," +
                    " pl.log_time as last_log_time," +
                    " pl.event as last_event" +
                " FROM users u" +
                " INNER JOIN punch_logs pl ON pl.id = (" +
                    " SELECT id FROM punch_logs" +
                    " WHERE user_id = u.id" +
                    " ORDER BY log_time DESC LIMIT 1)" +
                " WHERE pl.event = 'IN'";

        return jdbc.query(sql, userRowMapper);
    }

    // Selecciona los datos de todos los usuarios de un grupo y sus logs asociados
    @Override
    public List<User> findAllByGroup(Integer groupId) {
        String sql =
                "SELECT" +
                    " u.*, " +
                    " pl.id as last_id," +
                    " pl.log_time as last_log_time," +
                    " pl.event as last_event" +
                " FROM users u" +
                " LEFT JOIN punch_logs pl ON pl.id = (" +
                    " SELECT id FROM punch_logs " +
                    " WHERE user_id = u.id" +
                    "  ORDER BY log_time DESC" +
                    " LIMIT 1)" +
                " WHERE u.group_id = ?";

        return jdbc.query(sql, userRowMapper, groupId);
    }

    // Selecciona los datos de un usuario por id y sus logs asociados
    @Override
    public Optional<User> findById(Integer id) {
        String sql =
                "SELECT" +
                    " u.*, " +
                    " pl.id as last_id," +
                    " pl.log_time as last_log_time," +
                    " pl.event as last_event" +
                " FROM users u" +
                " LEFT JOIN punch_logs pl ON pl.id = (" +
                    " SELECT id FROM punch_logs " +
                    " WHERE user_id = u.id" +
                    " ORDER BY log_time DESC" +
                    " LIMIT 1)" +
                " WHERE u.id = ?";

        List<User> results = jdbc.query(sql, userRowMapper, id);

        return returnOptional(results);
    }

    // Selecciona los datos de un usuario por qr_token y sus logs asociados
    @Override
    public Optional<User> findByToken(String token) {
        String sql =
                "SELECT" +
                    " u.*, " +
                    " pl.id as last_id," +
                    " pl.log_time as last_log_time," +
                    " pl.event as last_event" +
                " FROM users u" +
                " LEFT JOIN punch_logs pl ON pl.id = (" +
                    " SELECT id FROM punch_logs " +
                    " WHERE user_id = u.id" +
                    " ORDER BY log_time DESC" +
                    " LIMIT 1)" +
                " WHERE u.qr_token = ?";

        List<User> results = jdbc.query(sql, userRowMapper, token);

        return returnOptional(results);
    }

    // Crea un nuevo usuario en la BD
    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (group_id, qr_token, username, password, role, name, surname) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
