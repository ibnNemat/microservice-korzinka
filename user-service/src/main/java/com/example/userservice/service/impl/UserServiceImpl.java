package com.example.userservice.service.impl;

import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import com.example.userservice.service.manualMappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseDto<List<UserDto>> getAllUser() {
        try {
            List<UserDto> users = userRepository.findAll().stream().map(UserMapper::toDto).toList();
            return ResponseDto.<List<UserDto>>builder()
                    .code(0).success(true).message("OK")
                    .responseData(users)
                    .build();
        } catch (Exception o){
            o.printStackTrace();
            return ResponseDto.<List<UserDto>>builder()
                    .code(-1).success(false).message("NO")
                    .build();
        }
    }

    @Override
    public ResponseDto<UserDto> getUserById(Integer id) {
        try {
            UserDto userDto = userRepository.findById(id).map(UserMapper::toDto).orElseThrow();

            return ResponseDto.<UserDto>builder()
                    .code(0).success(true).message("OK")
                    .responseData(userDto)
                    .build();
        } catch (Exception i){
            i.printStackTrace();
            return ResponseDto.<UserDto>builder()
                    .code(-1).success(false).message("NO")
                    .build();
        }
    }

    @Override
    public ResponseDto deleteUserById(Integer id) {
        try {
            userRepository.deleteById(id);
            return ResponseDto.builder()
                    .code(0).success(true).message("OK")
                    .build();
        } catch (Exception i){
            i.printStackTrace();
            return ResponseDto.builder()
                    .code(-1).success(false).message("NO")
                    .build();
        }
    }

    @Override
    public ResponseDto updateUser(UserDto userDto) {
        try {
            userRepository.save(UserMapper.toEntity(userDto));
            return ResponseDto.builder()
                    .code(0).success(true).message("OK")
                    .build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDto.builder()
                    .code(-1).success(false).message("NO")
                    .build();
        }
    }

    @Override
    public ResponseDto addUser(UserDto userDto) {
        try {
            userRepository.save(UserMapper.toEntity(userDto));
            return ResponseDto.builder()
                    .code(0).success(true).message("OK")
                    .build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDto.builder()
                    .code(-1).success(false).message("Not working")
                    .build();
        }
    }
}
