package app.opunch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScannerController {

    @GetMapping("/scanner")
    public String showScanner() {
        // Renderiza la nueva plantilla scanner.html
        return "scanner";
    }
}