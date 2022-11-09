package uz.nt.deliveryservice.controller;

import uz.nt.deliveryservice.dto.RegionDto;
import uz.nt.deliveryservice.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ResponseDto;

import java.util.List;

@RestController
@RequestMapping("/region")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseDto<List<RegionDto>> getAll() {
        return regionService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseDto<RegionDto> getById(@PathVariable Integer id) {
        return regionService.getById(id);
    }

    @PostMapping
    public ResponseDto<RegionDto> save(@RequestBody RegionDto regionDto) {
        //TODO: @Valid
        return regionService.save(regionDto);
    }

    @PatchMapping
    public ResponseDto<RegionDto> update(@RequestBody RegionDto regionDto) {
        return regionService.update(regionDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<RegionDto> deleteById(@PathVariable Integer id) {
        return regionService.deleteById(id);
    }
}
