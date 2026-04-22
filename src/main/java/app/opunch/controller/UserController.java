package app.opunch.controller;

import app.opunch.model.User;
import app.opunch.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getUserStatusView());
        return "userlist";
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
