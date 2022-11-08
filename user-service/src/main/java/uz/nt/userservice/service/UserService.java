package uz.nt.userservice.service;

import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.dto.LoginDto;
import shared.libs.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {
    ResponseDto<List<UserDto>> getAllUser();

    ResponseDto<UserDto> getUserById(Integer id);

    ResponseDto<UserDto> deleteUserById(Integer id);

    ResponseDto updateUser(UserDto userDto);

    ResponseDto<UserDto> addUser(UserDto userDto);
    ResponseDto<JWTResponseDto> login(LoginDto loginDto);
    ResponseDto<UserDto> checkToken(String token);

    void export(HttpServletRequest request, HttpServletResponse response);
}
