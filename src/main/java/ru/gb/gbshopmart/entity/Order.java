package ru.gb.gbshopmart.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.gb.gbapi.common.enums.OrderStatus;
import ru.gb.gbshopmart.entity.common.InfoEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "shop_order")
@EntityListeners(AuditingEntityListener.class)
public class Order extends InfoEntity {

    @Column(name = "address")
    private String address;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "mail")
    private String mail;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus = OrderStatus.CREATED;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @ManyToMany
    @JoinTable(
            name = "product_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;

    public Order(Long id, int version, String createdBy,
                 LocalDateTime createdDate, String lastModifiedBy,
                 LocalDateTime lastModifiedDate, String address,
                 String firstname, String lastname, String phone,
                 String mail, OrderStatus orderStatus, LocalDate deliveryDate,
                 Set<Product> products) {
        super(id, version, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.address = address;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.mail = mail;
        this.orderStatus = orderStatus;
        this.deliveryDate = deliveryDate;
        this.products = products;
    }
}
