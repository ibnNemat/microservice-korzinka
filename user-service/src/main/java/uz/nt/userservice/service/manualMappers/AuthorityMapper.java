package uz.nt.userservice.service.manualMappers;

import shared.libs.dto.AuthorityDto;
import uz.nt.userservice.entity.Authority;

public class AuthorityMapper {
    public static AuthorityDto toDto(Authority authority) {
        return AuthorityDto.builder()
                .id(authority.getId())
                .permission(authority.getPermission())
                .build();
    }

    public static Authority toEntity(AuthorityDto authority) {
        return Authority.builder()
                .id(authority.getId())
                .permission(authority.getPermission())
                .build();
    }
}