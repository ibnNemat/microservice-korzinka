package com.example.userservice.service.manualMappers;

import com.example.userservice.entity.Authority;
import com.example.userservice.entity.UserAuthority;
import shared.libs.dto.AuthorityDto;
import shared.libs.dto.UserAuthorityDto;

public class AuthorityMapper {
    public static AuthorityDto toDto(Authority authority){
        return AuthorityDto.builder()
                .id(authority.getId())
                .permission(authority.getPermission())
                .build();
    }

    public static Authority toEntity(AuthorityDto authority){
        return Authority.builder()
                .id(authority.getId())
                .permission(authority.getPermission())
                .build();
    }
}
