package ru.gb.gbexternalapi.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.gb.gbexternalapi.dto.ProductDto;

import java.util.List;

@Component
public interface ProductApi {

    List<ProductDto> getProductList();
//
//    ResponseEntity<?> getProduct(Long id);
//
//    ResponseEntity<?> handlePost(ProductDto productDto);
//
//    ResponseEntity<?> handleUpdate(Long id, ProductDto productDto);
//
//    void deleteById(Long id);
}
