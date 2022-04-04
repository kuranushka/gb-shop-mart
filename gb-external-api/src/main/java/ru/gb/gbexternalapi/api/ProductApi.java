package ru.gb.gbexternalapi.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.gb.gbexternalapi.dto.ProductDto;

import java.util.List;

@Component
public interface ProductApi {

    List<ProductDto> getProductList();
}
