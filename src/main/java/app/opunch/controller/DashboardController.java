package app.opunch.controller;

import app.opunch.service.PunchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// TEMPORAL - Se comunica con el PunchService para obtener una lista con todos los logs y enviarselos a dashboard.html
@Controller
public class DashboardController {

    private final PunchService punchService;

    public DashboardController(PunchService punchService) {
        this.punchService = punchService;
    }

    @GetMapping("/")
    public String viewDashboard(Model model) {
        model.addAttribute("logs", punchService.getAllLogs());
        return "dashboard";
    }
}