package app.opunch.controller;

import app.opunch.dto.PrintableUserDTO;
import app.opunch.service.PunchService;
import app.opunch.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

// TEMPORAL - Se comunica con el PunchService para obtener una lista con todos los logs y enviarselos a log-history.html
@Controller
public class DashboardController {

    private final PunchService punchService;
    private final UserService userService;

    public DashboardController(PunchService punchService, UserService userService) {
        this.punchService = punchService;
        this.userService = userService;
    }

    @GetMapping("/logs")
    public String viewLogs(Model model) {
        model.addAttribute("logs", punchService.getAllLogs());
        return "log-history";
    }

    @GetMapping("/sessions")
    public String viewSessions(Model model) {

        model.addAttribute("users", userService.getAllPrintableUsers());

        return "sessions";
    }
}