package app.opunch.service;

import app.opunch.model.User;
import app.opunch.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getUserStatusView() {
        return userRepo.findAll();
    }

    public void save(User user) {
        userRepo.save(user);
    }
}
