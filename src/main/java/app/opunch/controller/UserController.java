package app.opunch.controller;

import app.opunch.config.UserCustomDetails;
import app.opunch.model.PunchLog;
import app.opunch.model.User;
import app.opunch.model.WorkSession;
import app.opunch.service.PunchService;
import app.opunch.service.UserService;
import app.opunch.service.WorkSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final PunchService punchService;
    private final WorkSessionService sessionService;

    public UserController(UserService userService, PunchService punchService, WorkSessionService sessionService) {
        this.userService = userService;
        this.punchService = punchService;
        this.sessionService = sessionService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getUserStatusView());
        return "userlist";
    }

    // /users/id
    @GetMapping("/profile/{userId}")
    public String showProfile(@PathVariable Integer userId, Model model) {

        User user = userService.getUser(userId);
        model.addAttribute("user", user);

        //List<PunchLog> userLogs = punchService.getAllFromUser(userId);

        List<WorkSession> userSessions = sessionService.getAllFromUser(userId);
        Integer [] totalTime = sessionService.totalTime(userSessions);

        //model.addAttribute("logs", userLogs);

        model.addAttribute("sessions", userSessions);
        model.addAttribute("totalHours", totalTime[0]);
        model.addAttribute("totalMinutes", totalTime[1]);

        model.addAttribute("now", LocalDateTime.now());

        return "profile";
    }

    @GetMapping("/users/new")
    public String showCreateForm(Model model) {
        User newUser = new User();
        // We set the role to 3 so the new user form takes it as the default selection
        newUser.setRole(3);

        model.addAttribute("user", newUser);
        return "userform";
    }

    @PostMapping("/users/edit")
    public String showEditForm(@RequestParam("userId") Integer userId, Model model) {

        User editUser = userService.getFullUser(userId);

        editUser.setPassword("");

        model.addAttribute("user", editUser);
        return "useredit";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute("user") User user) {

        // Guardamos en la base de datos
        userService.createUser(user);

        // edirigimos a la lista de usuarios
        return "redirect:/users";
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute("user") User clientUser, Authentication authentication) {

        // 1. Obtener los detalles del usuario logueado
        UserCustomDetails loggedUser = (UserCustomDetails) authentication.getPrincipal();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        // 2. Seguridad Crítica: Si no es admin y el ID no coincide, es un ataque
        if (!isAdmin && clientUser.getId() != loggedUser.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes editar a otros usuarios");
        }

        User updatedUser = new User();

        if (!isAdmin) {
            User oldUser = userService.getUser(clientUser.getId());

            updatedUser.setId(oldUser.getId());
            updatedUser.setName(oldUser.getName());
            updatedUser.setSurname(oldUser.getSurname());
            updatedUser.setRole(oldUser.getRole());
            updatedUser.setToken(oldUser.getToken());

            updatedUser.setUsername(clientUser.getUsername());
            updatedUser.setPassword(clientUser.getPassword());
        } else {
            updatedUser = clientUser;
        }

        System.out.println("Update petition received");
        // Guardamos en la base de datos
        userService.updateUser(updatedUser);

        // Redirigimos a la lista de usuarios
        return "redirect:/profile/" + updatedUser.getId();
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam("userId") Integer id) {

        // Borramos el usuario seleccionado
        userService.deleteUser(id);

        // Redirigimos a la lista de usuarios
        return "redirect:/users";
    }


}
