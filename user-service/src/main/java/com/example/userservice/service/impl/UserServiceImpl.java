package com.example.userservice.service.impl;

import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import com.example.userservice.service.manualMappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shared.libs.dto.UserDto;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<List<UserDto>> getAllUser() {
        try {
            List<UserDto> users = userRepository.findAll().stream().map(UserMapper::toDto).toList();
            return ResponseEntity.ok().body(users);
        } catch (Exception o){
            o.printStackTrace();
            return ResponseEntity.status(-1).body(null);
        }
    }

    @Override
    public ResponseEntity<UserDto> getUserById(Integer id) {
        try {
            UserDto userDto = userRepository.findById(id).map(UserMapper::toDto).orElseThrow();

            return ResponseEntity.ok(userDto);
        } catch (Exception i){
            i.printStackTrace();
            return ResponseEntity.status(-1).body(null);
        }
    }

    @Override
    public ResponseEntity deleteUserById(Integer id) {
        try {
            userRepository.deleteById(id);
            return ResponseEntity.ok(null);
        } catch (Exception i){
            i.printStackTrace();
            return ResponseEntity.status(-1).body(null);
        }
    }

    @Override
    public ResponseEntity updateUser(UserDto userDto) {
        try {
            userRepository.save(UserMapper.toEntity(userDto));
            return ResponseEntity.ok(null);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(-1).body(null);
        }
    }

    @Override
    public ResponseEntity addUser(UserDto userDto) {
        try {
            userRepository.save(UserMapper.toEntity(userDto));
            return ResponseEntity.ok(null);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(-1).body(null);
        }
    }
}
