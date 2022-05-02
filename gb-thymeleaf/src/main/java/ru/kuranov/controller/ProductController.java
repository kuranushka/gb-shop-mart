package ru.kuranov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.gb.gbapi.category.dto.CategoryDto;
import ru.gb.gbapi.manufacturer.dto.ManufacturerDto;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.kuranov.model.dto.ProductNewDto;
import ru.kuranov.model.mapper.interfaces.ProductDtoMapper;
import ru.kuranov.service.gateway.CategoryGateway;
import ru.kuranov.service.impl.CategoryService;
import ru.kuranov.service.gateway.ManufacturerGateway;
import ru.kuranov.service.impl.ProductService;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;
    private final ProductDtoMapper productDtoMapper;
    private final CategoryService categoryService;
    private final CategoryGateway categoryGateway;
    private final ManufacturerGateway manufacturerGateway;


    @GetMapping("/app/products/{id}")
    public String viewProduct(Model model, @PathVariable Long id) {
        ProductDto productDto = productService.findById(id);
        ProductNewDto product = productDtoMapper.getProductNewDto(productDto);
        model.addAttribute("product", product);
        return "product-view";

    }

    @GetMapping("/app/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/app/products";
    }

    @GetMapping("/app/products/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        ProductNewDto productNewDto = productDtoMapper.getProductNewDto(productService.findById(id));
        model.addAttribute("categoryList", categoryService.findAll());
        model.addAttribute("product", productNewDto);
        return "product-edit";
    }

    @PostMapping("/app/products/edit/{id}")
    public String editProduct(@Valid ProductNewDto productNewDto,
                              BindingResult bindingResult,
                              RedirectAttributes attributes,
                              @PathVariable Long id) {

        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("errors", getErrors(bindingResult));
            attributes.addFlashAttribute("id", productNewDto.getId());
            return "redirect:/app/products/edit/{id}";
        } else {
            saveProduct(productNewDto, id);
            return "redirect:/app/products";
        }
    }

    //TODO
    @GetMapping("/app/products/add")
    public String addProduct(Model model) {
        ProductNewDto productNewDto = ProductNewDto.builder()
                .categories(categoryGateway.getCategories()
                        .stream()
                        .map(CategoryDto::getTitle)
                        .collect(Collectors.toList()))
                .manufacturers(manufacturerGateway.findManufacturers()
                        .stream()
                        .map(ManufacturerDto::getName)
                        .collect(Collectors.toList()))
                .build();
        model.addAttribute("product", productNewDto);
        return "product-add";
    }

    @PostMapping("/app/products/add")
    public String addProduct(@Valid ProductNewDto productNewDto,
                             BindingResult bindingResult,
                             RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("errors", getErrors(bindingResult));
            return "redirect:/app/products/add";
        } else {
            productService.save(productDtoMapper.getProductDto(productNewDto));
            return "redirect:/app/products";
        }
    }


    private Set<String> getErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toSet());
    }

    private void saveProduct(ProductNewDto productNewDto, Long id) {
        ProductDto productDto = productDtoMapper.getProductDto(productNewDto);
        productService.save(id, productDto);
    }
}
