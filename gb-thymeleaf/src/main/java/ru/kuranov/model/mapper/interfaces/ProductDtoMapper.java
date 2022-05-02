package ru.kuranov.model.mapper.interfaces;

import org.springframework.stereotype.Service;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.kuranov.model.dto.ProductNewDto;

@Service
public interface ProductDtoMapper {
    ProductDto getProductDto(ProductNewDto productNewDto);

    ProductNewDto getProductNewDto(ProductDto productDto);

    ProductDto getProduct(ProductDto productDto);
}
