package app.opunch.service;

import app.opunch.model.PunchLog;
import app.opunch.model.User;
import app.opunch.model.WorkSession;
import app.opunch.repository.PunchLogRepository;
import app.opunch.repository.UserRepository;
import app.opunch.repository.WorkSessionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// Gestiona la lógica de toggle (Punch IN / Punch OUT) conectándose a la BD a través de los Repositories
@Service
public class PunchService {

    // Repositorios para acceder a la BD
    private final UserRepository userRepo;
    private final PunchLogRepository logRepo;
    private final WorkSessionRepository wsRepo;

    // Variables de entorno
    @Value("${max.session.duration:480}")
    private Integer maxSessionDuration;

    @Value("${min.session.duration:5}")
    private Integer minSessionDuration;


    // Constructor
    public PunchService(UserRepository userRepo, PunchLogRepository logRepo, WorkSessionRepository wsRepo) {
        this.userRepo = userRepo;
        this.logRepo = logRepo;
        this.wsRepo = wsRepo;
    }

    // Toggle logic - Creates logs and worksessions in the database depending on the User's last log
    @Transactional
    public User togglePunch(String token) throws RuntimeException {

        // 1. Searches the User in the repo by token, Optional is empty if query returns no user
        Optional<User> userOptional = userRepo.findByToken(token);

        // Checks whether the Optional is full, throws an exception if is not
        if (userOptional.isEmpty()) {
            throw new RuntimeException("The token " + token + " doesn't exist");
        }

        // 2. Creates a User object from the Optional
        User user = userOptional.get();

        // 3. Saves the user state
        boolean active = user.isActive();

        // 4. Creates a new log object
        PunchLog newLog = new PunchLog();
        newLog.setUserId(user.getId());
        String nextEvent = active ? "OUT" : "IN";
        newLog.setEvent(nextEvent);

        // 6. Sends the new log with basic information to the database
        logRepo.save(newLog);

        // 7. Retrieves the same log with the automated database fields (id, log_time)
        PunchLog fullLog = logRepo.findNumberByUser(user.getId(), 1).getFirst();

        // 8. Opens or closes the worksession accordingly
        if (active) {
            closeWorkSession(fullLog);
        }
        else {
            openWorkSession(fullLog);
        }
        user.setLastLog(fullLog);

        return user;
    }

    private void openWorkSession (PunchLog startLog) {
        System.out.println("Method openWorkSession - Log ID:" + startLog.getId());
        wsRepo.openSession(startLog.getUserId(), startLog.getId(), startLog.getLogTime());
    }

    private boolean closeWorkSession (PunchLog endLog) throws RuntimeException {
        System.out.println("Method closeWorkSession - Log ID:" + endLog.getId());
        Optional <WorkSession> optionalWs = wsRepo.findOpenSession(endLog.getUserId());

        if (optionalWs.isEmpty()) {
            System.err.println("WARNING: PunchService.closeWorkSession didn't find an open session for user " + endLog.getUserId());
            return false;
        }

        WorkSession ws = optionalWs.get();
        Integer wsId = ws.getId();

        ws.setEndTime(endLog.getLogTime());

        Integer duration = ws.calculateDuration();

        if (duration < minSessionDuration) {
            System.out.println("Session " + wsId + " was shorter than MIN_SESSION_DURATION so it was automatically removed.");

            try {
                logRepo.remove(ws.getStartLogId());
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            try {
                wsRepo.removeSession(wsId);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            return false;
        }

        if (duration > maxSessionDuration) {
            duration = maxSessionDuration;
            System.out.println("WARNING: Session " + wsId + " was longer than " + maxSessionDuration + " so the duration was clamped to " + maxSessionDuration);
        }

        ws.setDurationMinutes(duration);

        wsRepo.closeSession(wsId, endLog.getId(), ws.getEndTime(), ws.getDurationMinutes());
        return true;

    }

    // Obtén todos los logs
    public List<PunchLog> getAllLogs() {
        return logRepo.findAll();
    }

    // Obtén todos los logs de un user
    public List<PunchLog> getAllFromUser(Integer userId) {
        return logRepo.findAllByUser(userId);
    }
}