package vn.hcmute.de4.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.hcmute.de4.model.User_24162037;

@Controller
public class ProfileController_24162037 {
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User_24162037 user = (User_24162037) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        return "profile";
    }
}
