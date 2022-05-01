package ru.gb.gbshopmart.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.gbapi.order.dto.OrderDto;
import ru.gb.gbshopmart.entity.Order;

@Mapper(uses = {ProductMapper.class, ManufacturerMapper.class})
public interface OrderMapper {

    Order toOrder(OrderDto orderDto);

    OrderDto toOrderDto(Order order);
}
