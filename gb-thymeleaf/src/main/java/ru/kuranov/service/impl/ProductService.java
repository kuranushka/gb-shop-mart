package ru.kuranov.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.kuranov.model.mapper.interfaces.ProductDtoMapper;
import ru.kuranov.service.gateway.ProductGateway;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductGateway productGateway;
    private final ProductDtoMapper productDtoMapper;

    public List<ProductDto> getProducts() {
        return productGateway.getProducts();
    }

    public ProductDto findById(Long id) {
        return productGateway.getProduct(id);
    }

    public void deleteById(Long id) {
        productGateway.deleteProduct(id);
    }

    public void save(Long id, ProductDto productDto) {
        productGateway.updateProduct(id, productDto);
    }

    public void save(ProductDto productDto) {
        productGateway.createProduct(productDto);
    }


    @Transactional(readOnly = true)
    public Long findMinCost() {
        return getProducts()
                .stream()
                .map(productDto -> productDto.getCost().longValue())
                .min(Comparator.comparing(Long::longValue))
                .orElseThrow();
    }


    @Transactional(readOnly = true)
    public Long findMaxCost() {
        return getProducts()
                .stream()
                .map(productDto -> productDto.getCost().longValue())
                .min(Comparator.comparing(Long::longValue))
                .orElseThrow();
    }

    public List<ProductDto> findAll() {
        return getProducts()
                .stream()
                .map(productDtoMapper::getProduct)
                .collect(Collectors.toList());
    }
}
