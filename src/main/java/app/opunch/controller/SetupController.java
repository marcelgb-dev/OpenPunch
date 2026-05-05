package app.opunch.controller;

import app.opunch.model.User;
import app.opunch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/setup")
public class SetupController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String showSetupForm() {
        return "setup-admin"; // Tu plantilla Thymeleaf
    }

    @PostMapping("/admin")
    public String firstSetup(@RequestParam String adminName,
                             @RequestParam String adminUser,
                             @RequestParam String adminPass,
                             @RequestParam String scannerUser,
                             @RequestParam String scannerPass) {

        User admin = new User();
        admin.setName(adminName);
        admin.setUsername(adminUser);
        admin.setPassword(adminPass);
        admin.setRole(1);

        userService.createUser(admin);

        User scanner = new User();
        scanner.setName("Scanner_1");
        scanner.setUsername(scannerUser);
        scanner.setPassword(scannerPass);
        scanner.setRole(2);

        userService.createUser(scanner);

        System.out.println("LOG - SETUP FINISHED");

        return "redirect:/login";
    }
}
