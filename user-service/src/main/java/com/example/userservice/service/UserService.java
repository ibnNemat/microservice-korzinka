package com.example.userservice.service;

import org.springframework.http.ResponseEntity;
import shared.libs.dto.UserDto;

import java.util.List;

public interface UserService {
    ResponseEntity<List<UserDto>> getAllUser();

    ResponseEntity<UserDto> getUserById(Integer id);

    ResponseEntity deleteUserById(Integer id);

    ResponseEntity updateUser(UserDto userDto);

    ResponseEntity addUser(UserDto userDto);
}
