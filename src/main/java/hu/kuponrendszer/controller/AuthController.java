package hu.kuponrendszer.controller;

import hu.kuponrendszer.dto.RegisterForm;
import hu.kuponrendszer.model.AppUser;
import hu.kuponrendszer.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    public AuthController(UserRepository users, PasswordEncoder encoder) { this.users = users; this.encoder = encoder; }

    @GetMapping("/") public String home() { return "redirect:/coupons"; }
    @GetMapping("/login") public String login() { return "login"; }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") RegisterForm form, BindingResult errors) {
        String email = form.getEmail() == null ? "" : form.getEmail().trim().toLowerCase();
        if (users.existsByEmail(email)) errors.rejectValue("email", "duplicate", "Ezzel az e-mail-címmel már van fiók.");
        if (errors.hasErrors()) return "register";
        users.save(new AppUser(form.getName().trim(), email, encoder.encode(form.getPassword())));
        return "redirect:/login?registered";
    }
}
