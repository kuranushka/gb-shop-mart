package ru.gb.gbshopmart.jmslistener;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.gb.gbapi.event.OrderEvent;

@Component
@RequiredArgsConstructor
public class OrderListener {

    private final OrderMailSender orderMailSender;

    @JmsListener(destination = "${gb-jms.properties.message.link}")
    public void listen(@Payload OrderEvent orderEvent) {
        orderMailSender.sendSimpleMessage(orderEvent);
    }
}
