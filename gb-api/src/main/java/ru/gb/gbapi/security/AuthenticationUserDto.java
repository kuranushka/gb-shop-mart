package ru.gb.gbapi.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationUserDto {

    private String username;
    private String password;
}
