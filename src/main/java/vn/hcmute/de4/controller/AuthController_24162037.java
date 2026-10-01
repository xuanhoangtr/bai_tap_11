package vn.hcmute.de4.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.hcmute.de4.model.LoginForm_24162037;
import vn.hcmute.de4.model.User_24162037;
import vn.hcmute.de4.service.UserService_24162037;

@Controller
public class AuthController_24162037 {
    private final UserService_24162037 userService;
    public AuthController_24162037(UserService_24162037 userService) { this.userService = userService; }
    @GetMapping("/login") public String login(@RequestParam(name="error", required=false) String error, @RequestParam(name="user", required=false) String user, @RequestParam(name="verified", required=false) String verified, Model model) { model.addAttribute("loginForm", new LoginForm_24162037()); if (error != null) model.addAttribute("error", "Thông tin đăng nhập không hợp lệ hoặc bạn chưa có quyền."); if (user != null) model.addAttribute("message", "Tài khoản User đã đăng nhập. Bạn quay lại trang đăng nhập để tiếp tục."); if (verified != null) model.addAttribute("message", "Xác thực OTP thành công. Vui lòng đăng nhập."); return "auth/login"; }
    @PostMapping("/login") public String login(@ModelAttribute LoginForm_24162037 form, HttpSession session, Model model) {
        return userService.authenticate(form.getUsername(), form.getPassword()).map(user -> { session.setAttribute("currentUser", user); return user.isAdmin() ? "redirect:/admin" : "redirect:/"; }).orElseGet(() -> { model.addAttribute("loginForm", form); model.addAttribute("error", "Sai tài khoản, mật khẩu hoặc tài khoản chưa kích hoạt."); return "auth/login"; });
    }
    @GetMapping("/register") public String register(Model model) { model.addAttribute("user", new User_24162037()); return "auth/register"; }
    @PostMapping("/register") public String register(@ModelAttribute User_24162037 user, HttpSession session, Model model) {
        try { User_24162037 saved = userService.register(user); session.setAttribute("otpUsername", saved.getUsername()); return "redirect:/verify-otp"; }
        catch (IllegalArgumentException | IllegalStateException ex) { model.addAttribute("user", user); model.addAttribute("error", ex.getMessage()); return "auth/register"; }
    }
    @GetMapping("/verify-otp") public String verifyPage(HttpSession session, Model model) { model.addAttribute("username", session.getAttribute("otpUsername")); return "auth/verify-otp"; }
    @PostMapping("/verify-otp") public String verify(@RequestParam String username, @RequestParam String otp, HttpSession session, Model model) { if (userService.verifyOtp(username, otp)) { session.removeAttribute("otpUsername"); return "redirect:/login?verified=1"; } model.addAttribute("username", username); model.addAttribute("error", "OTP không đúng hoặc đã hết hạn."); return "auth/verify-otp"; }
    @GetMapping("/logout") public String logout(HttpSession session) { session.invalidate(); return "redirect:/"; }
}
