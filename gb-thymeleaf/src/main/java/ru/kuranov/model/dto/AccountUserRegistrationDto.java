package ru.kuranov.model.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Component
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountUserRegistrationDto {

    @NotBlank(message = "PLEASE FILL USERNAME FIELD")
    @Size(min = 4, message = "USERNAME MUST CONTAINS MINIMUM 4 SYMBOLS")
    private String username;

    @NotBlank(message = "PLEASE FILL PASSWORD FIELD")
    @Size(min = 4, message = "PASSWORD MUST CONTAINS MINIMUM 4 SYMBOLS")
    private String password;

}
