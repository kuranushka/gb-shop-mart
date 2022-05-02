package ru.gb.gbapi.order.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbapi.order.dto.OrderDto;

import java.util.List;

public interface OrderGateway {

    @GetMapping
    List<OrderDto> getOrderList();

    @GetMapping("/{OrderId}")
    ResponseEntity<OrderDto> getOrder(@PathVariable("OrderId") Long id);

    @PostMapping
    ResponseEntity<OrderDto> handlePost(@Validated @RequestBody OrderDto OrderDto);

    @PutMapping("/{OrderId}")
    ResponseEntity<OrderDto> handleUpdate(@PathVariable("OrderId") Long id,
                                          @Validated @RequestBody OrderDto OrderDto);

    @DeleteMapping("/{OrderId}")
    void deleteById(@PathVariable("OrderId") Long id);
}
