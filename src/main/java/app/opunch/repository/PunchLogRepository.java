package app.opunch.repository;

import app.opunch.model.PunchLog;
import java.util.List;

public interface PunchLogRepository extends BaseRepository {

    List<PunchLog> findAll();
    List<PunchLog> findAllByUser(Integer userId);
    List<PunchLog> findNumberByUser(Integer userId, int numberOfLogs);

    void save(PunchLog punchLog);
}
