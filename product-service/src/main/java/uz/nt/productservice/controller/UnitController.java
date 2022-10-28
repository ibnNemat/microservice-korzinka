package uz.nt.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UnitDto;
import uz.nt.productservice.service.UnitService;

@RestController
@RequestMapping("/unit")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @GetMapping("/pagination")
    public ResponseDto<Page<UnitDto>> pagination(@RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false) Integer size){
        return unitService.pagination(page, size);
    }

    @PostMapping()
    public ResponseDto<UnitDto> add(@RequestBody UnitDto unitDto){
        return unitService.add(unitDto);
    }

    @PutMapping("/update")
    public ResponseDto<UnitDto> update(@RequestBody UnitDto unitDto){
        return unitService.update(unitDto);
    }
}
