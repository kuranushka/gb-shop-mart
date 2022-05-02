package ru.gb.gbshopmart.jmslistener;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.gb.gbapi.event.OrderEvent;
import ru.gb.gbapi.order.dto.OrderDto;

@Service
@RequiredArgsConstructor
public class OrderMailSender {

    private final JavaMailSender mailSender;
    @Value("${gb-jms.properties.message.from}")
    private String from;
    @Value("${gb-jms.properties.message.subject}")
    private String subject;
    @Value("${gb-jms.properties.message.text}")
    private String text;

    public void sendSimpleMessage(OrderEvent orderEvent) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(orderEvent.getOrderDto().getMail());
        message.setSubject("ORDER WAS CHANGED");

        OrderDto orderDto = orderEvent.getOrderDto();
        String text = String.format("orderId: %s\naddress: %s\nfirstname: %s\nlastname: %s\nphone: %s\nmail: %s\norderStatus: %s\ndeliveryDate: %s\nproducts: %s\ncreatedDate: %s\nlastModifiedDate: %s\n",
                orderDto.getId(),
                orderDto.getAddress(),
                orderDto.getFirstname(),
                orderDto.getLastname(),
                orderDto.getPhone(),
                orderDto.getMail(),
                orderDto.getOrderStatus(),
                orderDto.getDeliveryDate(),
                orderDto.getProducts(),
                orderDto.getCreatedDate(),
                orderDto.getLastModifiedDate());


        message.setText(text);
        mailSender.send(message);
    }
}