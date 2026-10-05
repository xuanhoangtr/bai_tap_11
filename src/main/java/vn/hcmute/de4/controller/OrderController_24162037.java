package vn.hcmute.de4.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hcmute.de4.model.OrderStatus_24162037;
import vn.hcmute.de4.model.User_24162037;
import vn.hcmute.de4.service.OrderService_24162037;

@Controller
@RequestMapping("/orders")
public class OrderController_24162037 {
    private final OrderService_24162037 orderService;
    public OrderController_24162037(OrderService_24162037 orderService) { this.orderService = orderService; }
    @GetMapping
    public String history(@RequestParam(required = false) String status, HttpSession session, Model model) {
        User_24162037 user = currentUser(session);
        if (user == null) return "redirect:/login?error=login";

        String selectedStatus = OrderStatus_24162037.fromCode(status)
                .map(OrderStatus_24162037::getCode)
                .orElse(null);
        model.addAttribute("orders", orderService.findOrders(user.getUserId(), selectedStatus));
        model.addAttribute("orderStatuses", OrderStatus_24162037.values());
        model.addAttribute("selectedStatus", selectedStatus);
        model.addAttribute("invalidStatus", status != null && selectedStatus == null);
        return "orders/history";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, HttpSession session, Model model) {
        User_24162037 user = currentUser(session);
        if (user == null) return "redirect:/login?error=login";
        return orderService.findOrder(user.getUserId(), id)
                .map(order -> {
                    model.addAttribute("order", order);
                    return "orders/detail";
                })
                .orElse("redirect:/orders");
    }
    private User_24162037 currentUser(HttpSession session) { Object current = session.getAttribute("currentUser"); return current instanceof User_24162037 user ? user : null; }
}
