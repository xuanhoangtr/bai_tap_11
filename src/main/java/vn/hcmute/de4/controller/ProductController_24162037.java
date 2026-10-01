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
import vn.hcmute.de4.service.ProductService_24162037;

@Controller
public class ProductController_24162037 {
    private final ProductService_24162037 productService;
    private final CartService_24162037 cartService;
    public ProductController_24162037(ProductService_24162037 productService, CartService_24162037 cartService) { this.productService = productService; this.cartService = cartService; }
    @GetMapping("/products") public String list(@RequestParam(defaultValue="0") int page, Model model) { model.addAttribute("pageData", productService.findPage(page)); return "products/list"; }
    @GetMapping("/products/{id}") public String detail(@PathVariable Long id, Model model) { return productService.findById(id).map(product -> { model.addAttribute("product", product); return "products/detail"; }).orElse("redirect:/products"); }
    @PostMapping("/products/{id}/cart") public String add(@PathVariable Long id, @RequestParam(defaultValue="1") int quantity, HttpSession session, RedirectAttributes flash) {
        User_24162037 user = currentUser(session); if (user == null) return "redirect:/login?error=login";
        try { cartService.add(user.getUserId(), id, quantity); flash.addFlashAttribute("success", "Đã thêm sản phẩm vào giỏ hàng."); }
        catch (IllegalArgumentException ex) { flash.addFlashAttribute("error", ex.getMessage()); }
        return "redirect:/products";
    }
    private User_24162037 currentUser(HttpSession session) { Object current = session.getAttribute("currentUser"); return current instanceof User_24162037 user ? user : null; }
}
