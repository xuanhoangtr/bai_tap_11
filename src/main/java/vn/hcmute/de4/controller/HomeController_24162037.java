package vn.hcmute.de4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.hcmute.de4.service.CategoryService_24162037;
import vn.hcmute.de4.service.VideoService_24162037;

@Controller
public class HomeController_24162037 {
    private final CategoryService_24162037 categoryService;
    private final VideoService_24162037 videoService;
    public HomeController_24162037(CategoryService_24162037 categoryService, VideoService_24162037 videoService) { this.categoryService = categoryService; this.videoService = videoService; }
    @GetMapping("/") public String home(Model model) {
        model.addAttribute("categories", categoryService.findAllWithVideoCount());
        model.addAttribute("videos", videoService.findPage(null, 0, 3));
        return "home";
    }
}
