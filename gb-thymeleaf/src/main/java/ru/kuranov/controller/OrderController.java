package ru.kuranov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kuranov.model.dto.OrderDto;

import javax.validation.Valid;

@Controller
public class OrderController {

    @GetMapping("/app/products/order")
    public String viewOrders(Model model) {
        return "order-view";
    }

    @GetMapping("/app/products/ordering")
    public String ordering(Model model, OrderDto orderDto) {
        return "ordering";
    }

    @PostMapping("/app/products/ordering")
    public String ordering(@Valid OrderDto orderDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/app/products/orderingg";
        }
        // сохраняем данные в другой микросервис
        return "redirect:/app/products";
    }
}
