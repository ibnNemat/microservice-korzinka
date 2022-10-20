package com.example.userservice.service.manualMappers;

import com.example.userservice.entity.User;
import com.example.userservice.entity.UserAuthority;
import shared.libs.dto.UserAuthorityDto;
import shared.libs.dto.UserDto;

public class UserAuthorityMapper {
    public static UserAuthorityDto toDto(UserAuthority userAuthority){
        return UserAuthorityDto.builder()
                .id(userAuthority.getId())
                .user_id(userAuthority.getUser_id())
                .authority_id(userAuthority.getAuthority_id())
                .build();
    }

    public static UserAuthority toEntity(UserAuthorityDto authorityDto){
        return UserAuthority.builder()
                .id(authorityDto.getId())
                .user_id(authorityDto.getUser_id())
                .authority_id(authorityDto.getAuthority_id())
                .build();
    }
}
