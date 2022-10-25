package uz.nt.userservice.service;

import uz.nt.userservice.dto.AuthorityDto;
import shared.libs.dto.ResponseDto;

import java.util.List;

public interface AuthorityService {
    ResponseDto<List<AuthorityDto>> getAllAuthority();

    ResponseDto<AuthorityDto> getAuthorityById(Integer id);

    ResponseDto deleteAuthorityById(Integer id);

    ResponseDto updateAuthority(AuthorityDto authorityDto);

    ResponseDto addAuthority(AuthorityDto authorityDto);
}
