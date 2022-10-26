package uz.nt.userservice.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.userservice.dto.AuthorityDto;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.repository.AuthorityRepository;
import uz.nt.userservice.service.AuthorityService;
import uz.nt.userservice.service.mapper.AuthorityMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

    @Override
    public ResponseDto<List<AuthorityDto>> getAllAuthority() {
        try {
            List<AuthorityDto> authorityDtos = authorityRepository.findAll().stream()
                    .map(authorityMapper::toDto).collect(Collectors.toList());
            return ResponseDto.<List<AuthorityDto>>builder()
                    .code(0).success(true).message("OK")
                    .responseData(authorityDtos)
                    .build();
        } catch (Exception o){
            o.printStackTrace();
            return ResponseDto.<List<AuthorityDto>>builder()
                    .code(-1).success(false).message("NO")
                    .build();
        }
    }

    @Override
    public ResponseDto<AuthorityDto> getAuthorityById(Integer id) {
        try {
            AuthorityDto authorityDto = authorityRepository.findById(id).map(authorityMapper::toDto).orElseThrow();

            return ResponseDto.<AuthorityDto>builder()
                    .code(0).success(true).message("OK")
                    .responseData(authorityDto)
                    .build();
        } catch (Exception i){
            i.printStackTrace();
            return ResponseDto.<AuthorityDto>builder()
                    .code(-1).success(false).message("NO")
                    .build();
        }
    }

    @Override
    public ResponseDto deleteAuthorityById(Integer id) {
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
    public ResponseDto updateAuthority(AuthorityDto authorityDto) {
        try {
            authorityRepository.save(authorityMapper.toEntity(authorityDto));
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
    public ResponseDto addAuthority(AuthorityDto authorityDto) {
        try {
            authorityRepository.save(authorityMapper.toEntity(authorityDto));
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
