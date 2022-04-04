package ru.gb.gbexternalapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.gbexternalapi.api.ProductApi;
import ru.gb.gbexternalapi.dto.ProductDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ShopController {

    private final ProductApi productApi;

    @GetMapping
    public List<ProductDto> getProductList() {
        return productApi.getProductList();
    }
}
