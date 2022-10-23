package com.example.deliveryservice.service.impl;

import com.example.deliveryservice.dto.LandmarkDto;
import com.example.deliveryservice.entity.Landmark;
import com.example.deliveryservice.repository.LandmarkRepository;
import com.example.deliveryservice.service.LandmarkService;
import com.example.deliveryservice.service.mapper.LandmarkMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LandmarkServiceImpl implements LandmarkService {

    private final LandmarkRepository landmarkRepository;

    @Override
    public ResponseDto<List<LandmarkDto>> getAllByCityId(Integer id) {
        try {
            List<Landmark> landmarks = landmarkRepository.findAllByCityId(id);

            if (landmarks.isEmpty())
                return ResponseDto.<List<LandmarkDto>>builder().code(404).success(false).message("Not found").build();

            return ResponseDto.<List<LandmarkDto>>builder()
                    .code(200)
                    .success(true)
                    .message("Found")
                    .responseData(LandmarkMap.toDtoList(landmarks))
                    .build();

        } catch (Exception e) {
            log.error("Landmark Service GetAllByCityId method");
            return ResponseDto.<List<LandmarkDto>>builder().code(0).success(false).message(e.getMessage()).build();
        }
    }
}
