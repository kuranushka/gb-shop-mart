package ru.gb.gbexternalapi.mapper;

import org.mapstruct.Mapper;
import ru.gb.gbapi.security.UserDto;
import ru.gb.gbexternalapi.security.AccountUser;

@Mapper
public interface UserMapper {

    AccountUser toAccountUser(UserDto userDto);

    UserDto toUserDto(AccountUser accountUser);
}
