package com.example.deliveryservice.service.impl;

import com.example.deliveryservice.dto.CityDto;
import com.example.deliveryservice.entity.City;
import com.example.deliveryservice.repository.CityRepository;
import com.example.deliveryservice.service.CityService;
import com.example.deliveryservice.service.mapper.CityMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public ResponseDto<List<CityDto>> getAllByRegionId(Integer id) {
        try {
            List<City> cities = cityRepository.findAllByRegionId(id);

            if (cities.isEmpty())
                return ResponseDto.<List<CityDto>>builder().code(404).success(false).message("Not found").build();

            return ResponseDto.<List<CityDto>>builder()
                    .code(200)
                    .success(true)
                    .message("Found")
                    .responseData(CityMap.toDtoList(cities))
                    .build();
            
        } catch (Exception e) {
            log.error("City Service GetAllByRegionId method");
            return ResponseDto.<List<CityDto>>builder().code(0).success(false).message(e.getMessage()).build();
        }
    }
}
