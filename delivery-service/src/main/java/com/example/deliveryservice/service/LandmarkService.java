package com.example.deliveryservice.service;

import com.example.deliveryservice.dto.LandmarkDto;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;

import java.util.List;

@Service
public interface LandmarkService {

    ResponseDto<List<LandmarkDto>> getAllByCityId(Integer id);
}
