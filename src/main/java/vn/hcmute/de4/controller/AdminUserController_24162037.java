package vn.hcmute.de4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.hcmute.de4.model.User_24162037;
import vn.hcmute.de4.service.UserService_24162037;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController_24162037 {
    private final UserService_24162037 userService;
    public AdminUserController_24162037(UserService_24162037 userService) { this.userService = userService; }
    @GetMapping public String list(@RequestParam(defaultValue="0") int page, Model model) { model.addAttribute("pageData", userService.findPage(page, 6)); return "admin/users/list"; }
    @GetMapping("/{id}") public String detail(@PathVariable Long id, Model model) { model.addAttribute("user", userService.findById(id).orElseThrow()); return "admin/users/detail"; }
    @GetMapping("/new") public String createForm(Model model) { model.addAttribute("user", new User_24162037()); model.addAttribute("newUser", true); return "admin/users/form"; }
    @GetMapping("/{id}/edit") public String editForm(@PathVariable Long id, Model model) { model.addAttribute("user", userService.findById(id).orElseThrow()); model.addAttribute("newUser", false); return "admin/users/form"; }
    @PostMapping("/save") public String save(@ModelAttribute User_24162037 user, @RequestParam(defaultValue="true") boolean newUser, Model model) { try { userService.saveAdmin(user, newUser); return "redirect:/admin/users"; } catch (IllegalArgumentException ex) { model.addAttribute("user", user); model.addAttribute("newUser", newUser); model.addAttribute("error", ex.getMessage()); return "admin/users/form"; } }
    @PostMapping("/{id}/delete") public String delete(@PathVariable Long id) { userService.delete(id); return "redirect:/admin/users"; }
}
