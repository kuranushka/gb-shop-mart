package ru.kuranov.model.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.gbapi.category.dto.CategoryDto;
import ru.gb.gbapi.manufacturer.dto.ManufacturerDto;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.kuranov.model.dto.ProductNewDto;
import ru.kuranov.model.mapper.interfaces.ProductDtoMapper;
import ru.kuranov.service.gateway.CategoryGateway;
import ru.kuranov.service.gateway.ManufacturerGateway;

import java.time.LocalDate;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ProductDtoMapperImpl implements ProductDtoMapper {

    private final CategoryGateway categoryGateway;
    private final ManufacturerGateway manufacturerGateway;

    @Override
    public ProductDto getProductDto(ProductNewDto productNewDto) {
        return ProductDto.builder()
                .id(productNewDto.getId())
                .title(productNewDto.getTitle())
                .manufacturer(productNewDto.getManufacturers().get(0))
                .manufactureDate(LocalDate.parse(productNewDto.getManufactureDate()))
                .categories(productNewDto.getCategories()
                        .stream()
                        .map(string -> CategoryDto.builder()
                                .title(string)
                                .build())
                        .collect(Collectors.toSet()))
                .cost(productNewDto.getCost())
                .status(productNewDto.getStatus())
                .build();
    }

    @Override
    public ProductNewDto getProductNewDto(ProductDto productDto) {
        return ProductNewDto.builder()
                .id(productDto.getId())
                .title(productDto.getTitle())
                .manufacturers(manufacturerGateway.findManufacturers()
                        .stream()
                        .map(ManufacturerDto::getName)
                        .collect(Collectors.toList()))
                .manufactureDate(productDto.getManufactureDate().toString())
                .categories(categoryGateway.getCategories()
                        .stream()
                        .map(CategoryDto::getTitle)
                        .collect(Collectors.toList()))
                .cost(productDto.getCost())
                .status(productDto.getStatus())
                .build();
    }

    @Override
    public ProductDto getProduct(ProductDto productDto) {
        return ProductDto.builder()
                .id(productDto.getId())
                .title(productDto.getTitle())
                .manufacturer(productDto.getManufacturer())
                .manufactureDate(productDto.getManufactureDate())
                .categories(productDto.getCategories()
                        .stream()
                        .map(categoryDto -> CategoryDto.builder()
                                .id(categoryDto.getId())
                                .title(categoryDto.getTitle())
                                .build())
                        .collect(Collectors.toSet()))
                .cost(productDto.getCost())
                .status(productDto.getStatus())
                .build();
    }
}
