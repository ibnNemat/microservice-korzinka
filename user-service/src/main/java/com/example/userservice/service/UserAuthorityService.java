package com.example.userservice.service;

import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserAuthorityDto;
import shared.libs.dto.UserDto;

import java.util.List;

public interface UserAuthorityService {
    ResponseDto<List<UserAuthorityDto>> getAllUserAuthority();

    ResponseDto<UserAuthorityDto> getUserAuthorityById(Integer id);

    ResponseDto deleteUserAuthorityById(Integer id);

    ResponseDto updateUserAuthority(UserAuthorityDto userAuthorityDto);

    ResponseDto addUserAuthority(UserAuthorityDto userAuthorityDto);
}
