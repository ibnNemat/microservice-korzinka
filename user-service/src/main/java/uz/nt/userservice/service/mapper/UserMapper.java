package uz.nt.userservice.service.mapper;

import org.mapstruct.Mapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import shared.libs.dto.UserDto;
import uz.nt.userservice.entity.Authority;
import uz.nt.userservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);
    UserDto toDto(User user);

    default SimpleGrantedAuthority convert(Authority authority){
        return new SimpleGrantedAuthority(authority.getPermission());
    }
}
