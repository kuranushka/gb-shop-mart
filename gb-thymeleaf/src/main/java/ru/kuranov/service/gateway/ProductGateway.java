package ru.kuranov.service.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbapi.product.dto.ProductDto;

import java.util.List;
import java.util.Map;

@Service
@FeignClient(value = "productGateway", url = "localhost:8081/external/api/v1/product")
public interface ProductGateway {

    @GetMapping
    List<ProductDto> getProducts();

    @GetMapping(value = "/{productId}")
    ProductDto getProduct(@PathVariable Long productId);

    @DeleteMapping(value = "/{productId}")
    void deleteProduct(@PathVariable Long productId);

    @PutMapping("/{productId}")
    void updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto);

    @PostMapping
    void createProduct(@RequestBody ProductDto productDto);
}
