package app.opunch.service;

import app.opunch.model.PunchLog;
import app.opunch.model.User;
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

    // 
}