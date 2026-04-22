package app.opunch.repository;

import app.opunch.model.WorkSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkSessionRepository extends BaseRepository {

    List<WorkSession> findAll();
    List<WorkSession> findAllByUser(Integer userId);

    Optional<WorkSession> findOpenSession(Integer userId);

    void openSession(Integer userId, Integer startLogId, LocalDateTime startTime);
    void closeSession(Integer workSessionId, Integer endLogId, LocalDateTime endTime, Integer durationMinutes);
}
