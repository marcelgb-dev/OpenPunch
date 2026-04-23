package app.opunch.controller;

import app.opunch.model.PunchLog;
import app.opunch.model.User;
import app.opunch.service.PunchService;
import app.opunch.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final PunchService punchService;

    public UserController(UserService userService, PunchService punchService) {
        this.userService = userService;
        this.punchService = punchService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getUserStatusView());
        return "userlist";
    }

    // /users/id
    @GetMapping("/users/profile/{userId}")
    public String showProfile(@PathVariable Integer userId, Model model) {

        User user = userService.getUser(userId);
        model.addAttribute("user", user);

        List<PunchLog> userLogs = punchService.getAllFromUser(userId);
        model.addAttribute("logs", userLogs);

        model.addAttribute("now", LocalDateTime.now());

        return "profile";
    }

    @GetMapping("/users/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "userform";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute("user") User user) {

        user.setQrToken(java.util.UUID.randomUUID().toString());
        // 2. Guardamos en la base de datos
        userService.save(user);

        // 3. Redirigimos a la lista de usuarios
        return "redirect:/users";
    }
}
