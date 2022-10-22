package uz.nt.userservice.service.mapper;

import org.mapstruct.Mapper;
import shared.libs.dto.AuthorityDto;
import uz.nt.userservice.entity.Authority;
@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    AuthorityDto toDto(Authority authority);

    Authority toEntity(AuthorityDto authority);
}