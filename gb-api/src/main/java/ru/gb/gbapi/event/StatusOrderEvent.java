package ru.gb.gbapi.event;

import lombok.*;
import ru.gb.gbapi.common.enums.OrderStatus;
import ru.gb.gbapi.order.dto.OrderDto;

@Getter
@Setter
@AllArgsConstructor
public class StatusOrderEvent extends OrderEvent {

    private OrderStatus prevStatus;

    @Builder
    public StatusOrderEvent(OrderDto orderDto, OrderStatus prevStatus) {
        super(orderDto);
        this.prevStatus = prevStatus;
    }
}
