package uz.nt.deliveryservice.controller;

import uz.nt.deliveryservice.dto.CityDto;
import uz.nt.deliveryservice.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shared.libs.dto.ResponseDto;

import java.util.List;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/{id}")
    public ResponseDto<List<CityDto>> getAllByRegionId(@PathVariable Integer id) {
        return cityService.getAllByRegionId(id);
    }
}
