package uz.nt.userservice.service;

import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.LoginDto;
import shared.libs.dto.UserDto;

import java.util.List;

public interface UserService {
    ResponseDto<List<UserDto>> getAllUser();

    ResponseDto<UserDto> getUserById(Integer id);

    ResponseDto deleteUserById(Integer id);

    ResponseDto updateUser(UserDto userDto);

    ResponseDto addUser(UserDto userDto);
    ResponseDto<JWTResponseDto> login(LoginDto loginDto);
    ResponseDto<UserDto> checkToken(String token);
}
