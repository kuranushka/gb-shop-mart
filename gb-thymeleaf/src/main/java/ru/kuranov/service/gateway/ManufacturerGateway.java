package ru.kuranov.service.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.gb.gbapi.manufacturer.dto.ManufacturerDto;

import java.util.List;

@FeignClient(value = "manufacturerGateway", url = "localhost:8081/external/api/v1/manufacturer")
public interface ManufacturerGateway {

    @GetMapping
    List<ManufacturerDto> findManufacturers();
}
