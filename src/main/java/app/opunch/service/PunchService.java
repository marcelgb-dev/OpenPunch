package app.opunch.service;

import app.opunch.model.PunchLog;
import app.opunch.model.User;
import app.opunch.repository.PunchLogRepository;
import app.opunch.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Gestiona la lógica de toggle (Punch IN / Punch OUT) conectándose a la BD a través de los Repositories
@Service
public class PunchService {

    // Repositorios para acceder a la BD
    private final UserRepository userRepo;
    private final PunchLogRepository logRepo;

    // Constructor
    public PunchService(UserRepository userRepo, PunchLogRepository logRepo) {
        this.userRepo = userRepo;
        this.logRepo = logRepo;
    }

    // Lógica de "toggle" para realizar la acción de IN o OUT
    public void togglePunch(String qrToken) throws RuntimeException {

        // 1. Buscamos al usuario por su token en el repo (usando Optional por si es nulo)
        Optional<User> userOptional = userRepo.findByToken(qrToken);

        // Comprobamos si el Optional contiene un valor. Si no, lanzamos una excepción.
        if (userOptional.isEmpty()) {
            throw new RuntimeException("El token " + qrToken + " no existe");
        }

        // 2. Guardamos el usuario desde el optional a una variable normal
        User user = userOptional.get();

        // 3. Decidimos el evento a ejecutar según si está activo o no
        String nextEvent = user.isActive() ? "OUT" : "IN";

        // 4. Creamos el nuevo log con dicho evento
        PunchLog newLog = new PunchLog();
        newLog.setUserId(user.getId());
        newLog.setEvent(nextEvent);

        // 5. Se lo enviamos al repo para que lo guarde en la BD
        logRepo.save(newLog);
    }

    // Obtén todos los logs
    public List<PunchLog> getAllLogs() {
        return logRepo.findAll();
    }
}