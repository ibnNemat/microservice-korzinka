package com.example.deliveryservice.service;

import com.example.deliveryservice.dto.RegionDto;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;

import java.util.List;

@Service
public interface RegionService {

    ResponseDto<List<RegionDto>> getAll();

    ResponseDto<RegionDto> getById(Integer id);

    ResponseDto<RegionDto> save(RegionDto regionDto);

    ResponseDto<RegionDto> update(RegionDto regionDto);

    ResponseDto<RegionDto> deleteById(Integer id);
}
