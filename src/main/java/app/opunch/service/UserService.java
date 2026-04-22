package app.opunch.service;

import app.opunch.model.User;
import app.opunch.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getUserStatusView() {
        return userRepo.findAll();
    }

    public User getUser(Integer id) {
        Optional<User> optionalUser = userRepo.findById(id);

        if (optionalUser.isEmpty())
        {
            System.out.println("The requested ID (" + id + ") does not belong to any current user");
            return null;
        }

        return optionalUser.get();
    }

    public void save(User user) {
        userRepo.save(user);
    }
}
