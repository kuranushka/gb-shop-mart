package ru.kuranov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.kuranov.service.CartService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final String QUANTITY_MESSAGE = "QUANTITY MUST BE A NUMBER AND GREATER THAN 0";


    @PostMapping("/app/products/addtocart")
    public String addToCart(@Valid ProductDto productDto,
                            BindingResult bindingResult,
                            RedirectAttributes attributes,
                            @RequestParam(required = false) String quantity) {
        if (quantity == null || quantity.isEmpty() || !cartService.validQuantity(quantity)) {
            attributes.addFlashAttribute("quantityNeed", QUANTITY_MESSAGE);
            return "redirect:/app/products";
        }
        cartService.addProductToCart(productDto.getId(), Long.valueOf(quantity));
        return "redirect:/app/products";
    }

    @GetMapping("/app/products/cart")
    public String cart(Model model) {
        Map<ProductDto, Long> products = cartService.getCartProducts();
        updateTotalCost(model);
        model.addAttribute("products", products);
        return "cart-view";
    }


    @PostMapping("/app/products/editcart")
    public String editCart(@Valid ProductDto productDto,
                           BindingResult bindingResult,
                           RedirectAttributes attributes,
                           Model model,
                           @RequestParam(required = false) String quantity) {

        if (quantity == null || quantity.isEmpty() || !cartService.validQuantity(quantity)) {
            attributes.addFlashAttribute("quantityNeed", QUANTITY_MESSAGE);
            return "redirect:/app/products/cart";
        }
        Map<ProductDto, Long> products = cartService.updateCart(productDto.getId(), Long.valueOf(quantity));
        updateTotalCost(model);
        model.addAttribute("products", products);
        return "cart-view";
    }

    @PostMapping("/app/products/deletefromcart/{productToDelete}")
    public String deleteProductFromCart(@PathVariable Long productToDelete) {
        cartService.deleteProductFromCart(productToDelete);
        return "redirect:/app/products/cart";
    }

    @GetMapping("/app/products/savecart")
    public String saveCart(Model model) {
        if (cartService.getCartProducts().isEmpty()) {
            model.addAttribute("message", "CART IS EMPTY... NOTHING TO SAVE");
        } else {
            cartService.saveCart();
            model.addAttribute("message", "THANK YOU!");
        }
        return "cart-saved";
    }


    @ModelAttribute
    private void updateTotalCost(Model model) {
        BigDecimal totalCost = cartService.getTotalCartCost();
        model.addAttribute("totalCost", totalCost);
    }
}
