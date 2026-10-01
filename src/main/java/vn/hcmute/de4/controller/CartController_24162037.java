package vn.hcmute.de4.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hcmute.de4.model.User_24162037;
import vn.hcmute.de4.service.CartService_24162037;

@Controller
public class CartController_24162037 {
    private final CartService_24162037 cartService;
    public CartController_24162037(CartService_24162037 cartService) { this.cartService = cartService; }
    @GetMapping("/cart") public String view(HttpSession session, Model model) {
        User_24162037 user = currentUser(session); if (user == null) return "redirect:/login?error=login";
        var items = cartService.getItems(user.getUserId()); model.addAttribute("items", items); model.addAttribute("total", cartService.total(items)); return "cart/view";
    }
    @PostMapping("/cart/{productId}/quantity") public String update(@PathVariable Long productId, @RequestParam int quantity, HttpSession session, RedirectAttributes flash) {
        User_24162037 user = currentUser(session); if (user == null) return "redirect:/login?error=login";
        try { cartService.update(user.getUserId(), productId, quantity); flash.addFlashAttribute("success", "Đã cập nhật số lượng."); }
        catch (IllegalArgumentException ex) { flash.addFlashAttribute("error", ex.getMessage()); }
        return "redirect:/cart";
    }
    @PostMapping("/cart/{productId}/remove") public String remove(@PathVariable Long productId, HttpSession session, RedirectAttributes flash) {
        User_24162037 user = currentUser(session); if (user == null) return "redirect:/login?error=login";
        cartService.remove(user.getUserId(), productId); flash.addFlashAttribute("success", "Đã xóa sản phẩm khỏi giỏ hàng."); return "redirect:/cart";
    }
    private User_24162037 currentUser(HttpSession session) { Object current = session.getAttribute("currentUser"); return current instanceof User_24162037 user ? user : null; }
}
