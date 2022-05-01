package ru.gb.gbshopmart.web.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbapi.common.enums.OrderStatus;
import ru.gb.gbapi.order.dto.OrderDto;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.gb.gbshopmart.service.OrderService;
import ru.gb.gbshopmart.service.ProductService;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping
    public List<OrderDto> getOrderList() {

//        ProductDto productDto1 = productService.findById(1L);
//        ProductDto productDto2 = productService.findById(2L);
//        ProductDto productDto3 = productService.findById(3L);
//
//        OrderDto orderDto = OrderDto.builder()
//                .address("Пономарева 16 кв 57")
//                .firstname("Андрей")
//                .lastname("Куранов")
//                .phone("+7 902 281 06 40")
//                .mail("kuranov.andrey@gmail.com")
//                .orderStatus(OrderStatus.CREATED)
//                .deliveryDate(LocalDate.of(2022, 6, 10))
//                .products(Set.of(productDto1, productDto2, productDto3))
//                .createdDate(LocalDateTime.of(2022, 5, 1, 19, 40))
//                .build();
//        orderService.save(orderDto);
        return orderService.findAll();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") Long id) {
        OrderDto orderDto;
        if (id != null) {
            orderDto = orderService.findById(id);
            if (orderDto != null) {
                return new ResponseEntity<>(orderDto, HttpStatus.OK);
            }
        }
        log.error("Retryer");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody OrderDto orderDto) {
        OrderDto savedOrder = orderService.save(orderDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/order/" + savedOrder.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("orderId") Long id, @Validated @RequestBody OrderDto orderDto) {
        orderDto.setId(id);
        orderService.save(orderDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("orderId") Long id) {
        orderService.deleteById(id);
    }
}
