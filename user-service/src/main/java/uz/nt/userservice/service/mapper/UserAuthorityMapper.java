package uz.nt.userservice.service.mapper;

import org.mapstruct.Mapper;
import shared.libs.dto.UserAuthorityDto;
import uz.nt.userservice.entity.UserAuthority;
@Mapper(componentModel = "spring")
public interface UserAuthorityMapper {
    UserAuthorityDto toDto(UserAuthority userAuthority);

    UserAuthority toEntity(UserAuthorityDto authorityDto);
}
