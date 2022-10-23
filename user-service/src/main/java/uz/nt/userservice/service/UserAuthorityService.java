package uz.nt.userservice.service;

import shared.libs.dto.ResponseDto;
import uz.nt.userservice.dto.UserAuthorityDto;

import java.util.List;

public interface UserAuthorityService {
    ResponseDto<List<UserAuthorityDto>> getAllUserAuthority();

    ResponseDto<UserAuthorityDto> getUserAuthorityById(Integer id);

    ResponseDto deleteUserAuthorityById(Integer id);

    ResponseDto updateUserAuthority(UserAuthorityDto userAuthorityDto);

    ResponseDto addUserAuthority(UserAuthorityDto userAuthorityDto);
}
