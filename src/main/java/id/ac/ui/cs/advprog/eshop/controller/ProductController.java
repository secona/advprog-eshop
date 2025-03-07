package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "CreateProduct";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product, Model model) {
        productService.create(product);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String productsListPage(Model model) {
        List<Product> productsList = productService.findAll();
        model.addAttribute("products", productsList);
        return "ListProduct";
    }

    @GetMapping("/edit/{productId}")
    public String editProductPage(@PathVariable String productId, Model model) {
        Optional<Product> product = productService.findOneById(productId);

        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "EditProduct";
        } else {
            return "ProductNotFound";
        }
    }

    @PostMapping("/edit/{productId}")
    public String editProduct(@PathVariable String productId, @ModelAttribute Product product) {
        productService.updateById(productId, product);
        return "redirect:../list";
    }

    @GetMapping("/delete/{productId}")
    public String deleteProductPage(@PathVariable String productId, Model model) {
        Optional<Product> product = productService.findOneById(productId);

        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "DeleteProduct";
        } else {
            return "ProductNotFound";
        }
    }

    @PostMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable String productId) {
        productService.deleteById(productId);
        return "redirect:../list";
    }
}
