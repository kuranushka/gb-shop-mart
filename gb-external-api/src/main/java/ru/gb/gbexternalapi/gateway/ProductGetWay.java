package ru.gb.gbexternalapi.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.gb.gbexternalapi.api.ProductApi;
import ru.gb.gbexternalapi.dto.ProductDto;

import java.util.List;

@FeignClient(url = "${gb-external-api.url.product}", value = "productApi")
public interface ProductGetWay extends ProductApi {

    @GetMapping(value = "/", produces = "application/json;charset=UTF-8")
    List<ProductDto> getProductList();
}
