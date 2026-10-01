package vn.hcmute.de4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hcmute.de4.model.Product_24162037;
import vn.hcmute.de4.service.ProductService_24162037;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController_24162037 {
    private final ProductService_24162037 productService;

    public AdminProductController_24162037(ProductService_24162037 productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("pageData", productService.findAdminPage(page));
        return "admin/products/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Product_24162037 product = new Product_24162037();
        product.setActive(true);
        model.addAttribute("product", product);
        model.addAttribute("newProduct", true);
        return "admin/products/form";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findAdminById(id).orElseThrow());
        return "admin/products/detail";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findAdminById(id).orElseThrow());
        model.addAttribute("newProduct", false);
        return "admin/products/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Product_24162037 product,
                       @RequestParam(defaultValue = "true") boolean newProduct,
                       @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                       Model model,
                       RedirectAttributes flash) {
        try {
            productService.saveAdmin(product, newProduct, imageFile);
            flash.addFlashAttribute("success", newProduct ? "Đã thêm sản phẩm." : "Đã cập nhật sản phẩm.");
            return "redirect:/admin/products";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("product", product);
            model.addAttribute("newProduct", newProduct);
            model.addAttribute("error", ex.getMessage());
            return "admin/products/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        productService.deleteFromCatalog(id);
        flash.addFlashAttribute("success", "Đã xóa sản phẩm khỏi cửa hàng. Lịch sử đơn hàng được giữ nguyên.");
        return "redirect:/admin/products";
    }
}
