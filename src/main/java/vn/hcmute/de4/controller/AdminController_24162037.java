package vn.hcmute.de4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hcmute.de4.service.CategoryService_24162037;
import vn.hcmute.de4.service.UserService_24162037;

@Controller
@RequestMapping("/admin")
public class AdminController_24162037 {
    private final UserService_24162037 userService;
    private final CategoryService_24162037 categoryService;
    public AdminController_24162037(UserService_24162037 userService, CategoryService_24162037 categoryService) { this.userService = userService; this.categoryService = categoryService; }
    @GetMapping public String dashboard(Model model) { model.addAttribute("userCount", userService.findPage(0, 1).getTotalItems()); model.addAttribute("categories", categoryService.findAllWithVideoCount()); return "admin/dashboard"; }
}
