package ru.kuranov.service.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.gb.gbapi.category.dto.CategoryDto;

import java.util.List;

@Service
@FeignClient(value = "categoryGateway", url = "localhost:8081/external/api/v1/category")
public interface CategoryGateway {

    @GetMapping
    List<CategoryDto> getCategories();
}
