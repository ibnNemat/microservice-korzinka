package uz.nt.userservice.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.userservice.dto.UserAuthorityDto;
import uz.nt.userservice.entity.UserAuthority;

@Mapper(componentModel = "spring")
public interface UserAuthorityMapper {
    UserAuthority toEntity(UserAuthorityDto userAuthorityDto);
    UserAuthorityDto toDto(UserAuthority userAuthority);
}
