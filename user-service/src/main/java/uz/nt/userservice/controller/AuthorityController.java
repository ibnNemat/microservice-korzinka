package uz.nt.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.userservice.dto.AuthorityDto;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.service.AuthorityService;

import java.util.List;

@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
public class AuthorityController {
    private final AuthorityService authorityService;

    //asdassdasdasda
    @GetMapping
    public ResponseDto<List<AuthorityDto>> getAll(){
        return authorityService.getAllAuthority();
    }

    @GetMapping("/{id}")
    public ResponseDto<AuthorityDto> getById(@PathVariable Integer id){
        return authorityService.getAuthorityById(id);
    }

    @PostMapping
    public ResponseDto addAuthority(@RequestBody AuthorityDto authorityDto){
        return authorityService.addAuthority(authorityDto);
    }

    @PutMapping
    public ResponseDto updateAuthority(@RequestBody AuthorityDto authorityDto){
        return authorityService.updateAuthority(authorityDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteAuthority(@PathVariable Integer id){
        return authorityService.deleteAuthorityById(id);
    }
}
