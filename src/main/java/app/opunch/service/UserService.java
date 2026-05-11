package app.opunch.service;

import app.opunch.model.User;
import app.opunch.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import java.sql.SQLException;
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

    public List<User> getAllUsers() {
        return userRepo.findAllFull();
    }

    public List<User> getUsersByRole(Integer role) {
        return userRepo.findByRole(role);
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

    public User getFullUser(Integer id) {
        Optional<User> optionalUser = userRepo.findByIdFull(id);

        if (optionalUser.isEmpty())
        {
            System.out.println("The requested ID (" + id + ") does not belong to any current user");
            return null;
        }

        return optionalUser.get();
    }

    // Returns a NanoID with default number and alphabet of 10 characters long
    public String newToken() {

        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        return NanoIdUtils.randomNanoId(
                NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
                alphabet.toCharArray(),
                10
        );
    }

    public void createUser(User user) {

        // Password encoding with BCrypt
        String rawPassword = user.getPassword();
        if (rawPassword != null)
            user.setPassword(encoder.encode(rawPassword));

        // Automatic Token generation (NanoID) if there's no manual entry
        if (user.getToken()== null || user.getToken().isEmpty())
            user.setToken(newToken());

        userRepo.save(user);
    }

    // Updates the information of an existing user
    public void updateUser(User user) {

        // Checks if the User exists
        if (getUser(user.getId()) == null) {
            System.out.println("User with ID " + user.getId() + " does not exist in the database.");
            return;
        }

        // Password encoding with BCrypt
        String rawPassword = user.getPassword();
        String newPassword = null;
        if (!rawPassword.isBlank()) {
            newPassword = encoder.encode(rawPassword);
        }

        user.setPassword(newPassword);

        // Updates the user fields with save()
        userRepo.save(user);
    }

    // Removes an existing user and their related work sessions + punch logs
    public void deleteUser(Integer userId) {
        try {
            userRepo.remove(userId);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
