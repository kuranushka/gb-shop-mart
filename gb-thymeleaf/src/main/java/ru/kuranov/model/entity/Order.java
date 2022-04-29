package ru.kuranov.model.entity;

import lombok.*;
import ru.gb.gbapi.common.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "address")
    private String address;

    @NotBlank
    @Column(name = "lastname")
    private String lastname;

    @NotBlank
    @Column(name = "firstname")
    private String firstname;

    @NotBlank
    @Column(name = "phone_number")
    private String phoneNumber;

    @Email
    @Column(name = "mail")
    private String mail;

    @Enumerated(EnumType.STRING)
    private Status status;
}
