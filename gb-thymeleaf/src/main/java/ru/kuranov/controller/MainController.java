package ru.kuranov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.kuranov.service.impl.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping("/app/products")
    public String allProducts(Model model) {
        List<ProductDto> productDtoList = productService.getProducts();
        model.addAttribute("list", productDtoList);
        return "product-view-all";
    }
}
