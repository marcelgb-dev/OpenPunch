package app.opunch.repository;

import app.opunch.model.PunchLog;
import java.util.List;

public interface PunchLogRepository {

    List<PunchLog> findAll();
    List<PunchLog> findAllByUser(Integer userId);

    PunchLog findLastByUser(Integer userId);
    PunchLog findById(Integer id);

    void save(PunchLog punchLog);
}
