package uz.nt.userservice.service.impl;

import uz.nt.userservice.repository.UserAuthorityRepository;
import uz.nt.userservice.service.UserAuthorityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.dto.UserAuthorityDto;
import uz.nt.userservice.service.mapper.UserAuthorityMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthorityServiceImpl implements UserAuthorityService {

    private final UserAuthorityRepository authorityRepository;
    private final UserAuthorityMapper userAuthorityMapper;

    @Override
    public ResponseDto<List<UserAuthorityDto>> getAllUserAuthority() {
        try {
            List<UserAuthorityDto> authorityDtos = authorityRepository.findAll()
                    .stream().map(userAuthorityMapper::toDto).collect(Collectors.toList());
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
            UserAuthorityDto authorityDto = authorityRepository.findById(id)
                    .map(userAuthorityMapper::toDto).orElseThrow();

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
            authorityRepository.save(userAuthorityMapper.toEntity(userAuthorityDto));
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
            authorityRepository.save(userAuthorityMapper.toEntity(userAuthorityDto));
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
