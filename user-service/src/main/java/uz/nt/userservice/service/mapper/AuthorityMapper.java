package uz.nt.userservice.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.userservice.dto.AuthorityDto;
import uz.nt.userservice.entity.Authority;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    Authority toEntity(AuthorityDto authorityDto);
    AuthorityDto toDto(Authority authority);
}
