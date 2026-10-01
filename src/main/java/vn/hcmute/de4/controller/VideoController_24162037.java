package vn.hcmute.de4.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.hcmute.de4.model.ShareForm_24162037;
import vn.hcmute.de4.model.User_24162037;
import vn.hcmute.de4.service.CategoryService_24162037;
import vn.hcmute.de4.service.InteractionService_24162037;
import vn.hcmute.de4.service.VideoService_24162037;

@Controller
public class VideoController_24162037 {
    private final VideoService_24162037 videoService;
    private final CategoryService_24162037 categoryService;
    private final InteractionService_24162037 interactionService;
    public VideoController_24162037(VideoService_24162037 videoService, CategoryService_24162037 categoryService, InteractionService_24162037 interactionService) { this.videoService = videoService; this.categoryService = categoryService; this.interactionService = interactionService; }
    @GetMapping("/videos") public String list(@RequestParam(required=false) Long categoryId, @RequestParam(defaultValue="0") int page, Model model) { model.addAttribute("pageData", videoService.findPage(categoryId, page, 3)); model.addAttribute("categoryId", categoryId); model.addAttribute("categories", categoryService.findAllWithVideoCount()); return "videos/list"; }
    @GetMapping("/videos/{id}") public String detail(@PathVariable Long id, HttpSession session, Model model) { User_24162037 user = (User_24162037) session.getAttribute("currentUser"); return videoService.findDetail(id, user == null ? null : user.getUserId()).map(video -> { model.addAttribute("video", video); model.addAttribute("shareForm", new ShareForm_24162037()); return "videos/detail"; }).orElse("redirect:/videos"); }
    @PostMapping("/videos/{id}/like") public String like(@PathVariable Long id, HttpSession session) { User_24162037 user = (User_24162037) session.getAttribute("currentUser"); if (user == null) return "redirect:/login"; interactionService.toggleLike(user.getUserId(), id); return "redirect:/videos/" + id; }
    @PostMapping("/videos/{id}/share") public String share(@PathVariable Long id, @RequestParam String email, HttpSession session) { User_24162037 user = (User_24162037) session.getAttribute("currentUser"); if (user == null) return "redirect:/login"; interactionService.share(user.getUserId(), id, email); return "redirect:/videos/" + id + "?shared=1"; }
}
