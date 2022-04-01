package ru.gb.gbexternalapi.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbexternalapi.api.ProductApi;
import ru.gb.gbexternalapi.dto.ProductDto;

import java.util.List;

@FeignClient(url = "localhost:8080/api/v1/product", value = "productApi")
public interface ProductGetWay extends ProductApi {

    @GetMapping(value = "/", produces = "application/json;charset=UTF-8")
    List<ProductDto> getProductList();


//    @GetMapping("/getProduct")
//    ResponseEntity<?> getProduct(@PathVariable("productId") Long id);
//
//
//    @PostMapping("/handlePost")
//    ResponseEntity<?> handlePost(@Validated @RequestBody ProductDto productDto);
//
//    @PutMapping("/handleUpdate")
//    ResponseEntity<?> handleUpdate(@PathVariable("productId") Long id, @Validated @RequestBody ProductDto productDto);
//
//    @DeleteMapping("/deleteById")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    void deleteById(@PathVariable("productId") Long id);

}
