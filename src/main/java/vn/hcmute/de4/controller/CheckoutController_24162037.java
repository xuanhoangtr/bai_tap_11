package vn.hcmute.de4.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hcmute.de4.model.CheckoutForm_24162037;
import vn.hcmute.de4.model.User_24162037;
import vn.hcmute.de4.service.CartService_24162037;
import vn.hcmute.de4.service.OrderService_24162037;

@Controller
@RequestMapping("/checkout")
public class CheckoutController_24162037 {
    private final CartService_24162037 cartService;
    private final OrderService_24162037 orderService;
    public CheckoutController_24162037(CartService_24162037 cartService, OrderService_24162037 orderService) { this.cartService = cartService; this.orderService = orderService; }
    @GetMapping public String checkout(HttpSession session, Model model, @RequestParam(required=false) String error) {
        User_24162037 user = currentUser(session); if (user == null) return "redirect:/login?error=login";
        var items = cartService.getItems(user.getUserId()); if (items.isEmpty()) return "redirect:/cart";
        CheckoutForm_24162037 form = new CheckoutForm_24162037(); form.setReceiverName(user.getFullname()); form.setReceiverPhone(user.getPhone());
        model.addAttribute("form", form); model.addAttribute("items", items); model.addAttribute("total", cartService.total(items));
        if (error != null) model.addAttribute("error", error);
        return "orders/checkout";
    }
    @PostMapping public String placeOrder(CheckoutForm_24162037 form, HttpSession session, RedirectAttributes flash) {
        User_24162037 user = currentUser(session); if (user == null) return "redirect:/login?error=login";
        try { var order = orderService.checkout(user.getUserId(), form); return "redirect:/orders/" + order.getOrderId(); }
        catch (IllegalArgumentException ex) { flash.addFlashAttribute("error", ex.getMessage()); return "redirect:/checkout"; }
    }
    private User_24162037 currentUser(HttpSession session) { Object current = session.getAttribute("currentUser"); return current instanceof User_24162037 user ? user : null; }
}
