package app.opunch.service;

import app.opunch.model.User;
import app.opunch.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
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

        // Password encoding with BCrypt
        String rawPassword = user.getPassword();
        if (rawPassword != null)
            user.setPassword(encoder.encode(rawPassword));

        // QR Token generation (UUID)
        user.setQrToken(java.util.UUID.randomUUID().toString());

        userRepo.save(user);
    }
}
