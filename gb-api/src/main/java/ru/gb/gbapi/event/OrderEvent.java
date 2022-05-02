package ru.gb.gbapi.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.gbapi.order.dto.OrderDto;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent implements Serializable {

    static final long serialVersionUID = -7329953247858218407L;

    private OrderDto orderDto;
}
