package app.opunch.service;

import app.opunch.model.PunchLog;
import app.opunch.model.User;
import app.opunch.model.WorkSession;
import app.opunch.repository.PunchLogRepository;
import app.opunch.repository.UserRepository;
import app.opunch.repository.WorkSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkSessionService {

    // Repositorios para acceder a la BD
    private final UserRepository userRepo;
    private final WorkSessionRepository wsRepo;

    // Constructor
    public WorkSessionService(UserRepository userRepo, WorkSessionRepository wsRepo) {
        this.userRepo = userRepo;
        this.wsRepo = wsRepo;
    }

    public List<WorkSession> getAllFromUser(Integer userId) {
        return wsRepo.findAllByUser(userId);
    }

    public Optional<WorkSession> getLastSession(Integer userId) {
        return wsRepo.findLastSession(userId);
    }

    // [0] = hours [1] = minutes
    public Integer[] totalTime(List<WorkSession> sessions) {
        Integer totalTime = 0;
        Integer totalHours = 0;
        Integer totalMinutes = 0;

        for (WorkSession ws : sessions) {
            totalTime += ws.getDurationMinutes();
        }

        totalHours = totalTime / 60;
        totalMinutes = totalTime % 60;

        return new Integer[]{totalHours, totalMinutes};
    }

    // 
}