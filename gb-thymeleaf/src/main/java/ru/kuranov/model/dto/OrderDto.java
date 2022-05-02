package ru.kuranov.model.dto;

import lombok.*;
import org.springframework.stereotype.Component;
import ru.gb.gbapi.common.enums.Status;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Component
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;

    @NotBlank(message = "ADDRESS MUST BE FILL")
    private String address;

    @NotBlank(message = "LASTNAME MUST BE FILL")
    private String lastname;

    @NotBlank(message = "FIRSTNAME MUST BE FILL")
    private String firstname;

    @NotBlank(message = "PHONE NUMBER MUST BE FILL")
    private String phoneNumber;

    @Email
    private String mail;

    private Status status;
}
