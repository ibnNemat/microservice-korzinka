package uz.nt.userservice.service.manualMappers;

import shared.libs.dto.UserAuthorityDto;
import shared.libs.dto.UserDto;
import uz.nt.userservice.entity.UserAuthority;

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
