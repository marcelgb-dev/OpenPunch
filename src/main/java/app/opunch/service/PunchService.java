package app.opunch.service;

import app.opunch.model.PunchLog;
import app.opunch.model.User;
import app.opunch.model.WorkSession;
import app.opunch.repository.PunchLogRepository;
import app.opunch.repository.UserRepository;
import app.opunch.repository.WorkSessionRepository;
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


    // Constructor
    public PunchService(UserRepository userRepo, PunchLogRepository logRepo, WorkSessionRepository wsRepo) {
        this.userRepo = userRepo;
        this.logRepo = logRepo;
        this.wsRepo = wsRepo;
    }

    // Toggle logic - Creates logs and worksessions in the database depending on the User's last log
    @Transactional
    public void togglePunch(String qrToken) throws RuntimeException {

        // 1. Searches the User in the repo by qrToken, Optional is empty if query returns no user
        Optional<User> userOptional = userRepo.findByToken(qrToken);

        // Checks whether the Optional is full, throws an exception if is not
        if (userOptional.isEmpty()) {
            throw new RuntimeException("The token " + qrToken + " doesn't exist");
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
    }

    private void openWorkSession (PunchLog startLog) {
        wsRepo.openSession(startLog.getUserId(), startLog.getId(), startLog.getLogTime());
    }

    private void closeWorkSession (PunchLog endLog) throws RuntimeException {

        Optional <WorkSession> optionalWs = wsRepo.findOpenSession(endLog.getUserId());

        if (optionalWs.isEmpty())
            throw new RuntimeException("PunchService.closeWorkSession didn't find an open session");

        WorkSession ws = optionalWs.get();
        ws.setEndTime(endLog.getLogTime());
        ws.setDurationMinutes(ws.calculateDuration());

        wsRepo.closeSession(ws.getId(), endLog.getId(), ws.getEndTime(), ws.getDurationMinutes());
    }

    // Obtén todos los logs
    public List<PunchLog> getAllLogs() {
        return logRepo.findAll();
    }
}