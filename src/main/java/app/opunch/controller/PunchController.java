package app.opunch.controller;

import app.opunch.service.PunchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Llama desde /punch a la lógica de toggle del PunchService. Guarda temporalmente el éxito o fracaso con FlashAttribute
@Controller
public class PunchController {

    private final PunchService punchService;

    public PunchController(PunchService punchService) {
        this.punchService = punchService;
    }

    // Ruta para el fichaje: /punch?token=XXXX
    @GetMapping("/punch")
    public String handlePunch(@RequestParam String token, RedirectAttributes flash) {
        try {
            punchService.togglePunch(token);
            flash.addFlashAttribute("success", "¡Fichaje realizado correctamente!");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al fichar: " + e.getMessage());
        }

        // Después de fichar, redirigimos a la página principal
        return "redirect:/";
    }
}