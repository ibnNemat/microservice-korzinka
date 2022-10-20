package com.example.userservice.service.impl;

import com.example.userservice.repository.UserAuthorityRepository;
import com.example.userservice.service.UserAuthorityService;
import com.example.userservice.service.manualMappers.UserAuthorityMapper;
import com.example.userservice.service.manualMappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserAuthorityDto;
import shared.libs.dto.UserDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAuthorityServiceImpl implements UserAuthorityService {

    private final UserAuthorityRepository authorityRepository;

    @Override
    public ResponseDto<List<UserAuthorityDto>> getAllUserAuthority() {
        try {
            List<UserAuthorityDto> authorityDtos = authorityRepository.findAll().stream().map(UserAuthorityMapper::toDto).toList();
            return ResponseDto.<List<UserAuthorityDto>>builder()
                    .code(0).success(true).message("OK")
                    .responseData(authorityDtos)
                    .build();
        } catch (Exception o){
            o.printStackTrace();
            return ResponseDto.<List<UserAuthorityDto>>builder()
                    .code(-1).success(false).message("NO")
                    .build();
        }
    }

    @Override
    public ResponseDto<UserAuthorityDto> getUserAuthorityById(Integer id) {
        try {
            UserAuthorityDto authorityDto = authorityRepository.findById(id).map(UserAuthorityMapper::toDto).orElseThrow();

            return ResponseDto.<UserAuthorityDto>builder()
                    .code(0).success(true).message("OK")
                    .responseData(authorityDto)
                    .build();
        } catch (Exception i){
            i.printStackTrace();
            return ResponseDto.<UserAuthorityDto>builder()
                    .code(-1).success(false).message("NO")
                    .build();
        }
    }

    @Override
    public ResponseDto deleteUserAuthorityById(Integer id) {
        try {
            authorityRepository.deleteById(id);
            return ResponseDto.builder()
                    .code(0).success(true).message("OK")
                    .build();
        } catch (Exception i){
            i.printStackTrace();
            return ResponseDto.builder()
                    .code(-1).success(false).message("Not work")
                    .build();
        }
    }

    @Override
    public ResponseDto updateUserAuthority(UserAuthorityDto userAuthorityDto) {
        try {
            authorityRepository.save(UserAuthorityMapper.toEntity(userAuthorityDto));
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
    public ResponseDto addUserAuthority(UserAuthorityDto userAuthorityDto) {
        try {
            authorityRepository.save(UserAuthorityMapper.toEntity(userAuthorityDto));
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
