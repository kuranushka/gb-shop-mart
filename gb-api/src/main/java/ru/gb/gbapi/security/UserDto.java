package ru.gb.gbapi.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @JsonIgnore
    private Long id;

    @NotBlank(message = "is required")
    @Size(min = 4, message = "username must be greater than 3 symbols")
    private String username;

    @NotBlank(message = "is required")
    @Size(min = 4, message = "password must be greater than 3 letter")
    private String password;

    @NotBlank(message = "is required")
    private String firstname;

    @NotBlank(message = "is required")
    private String lastname;

    @Size(min = 5, message = "phone must be greater than 4 symbols")
    @NotBlank(message = "is required")
    private String phone;

    @Email
    @NotBlank(message = "is required")
    private String email;
}

