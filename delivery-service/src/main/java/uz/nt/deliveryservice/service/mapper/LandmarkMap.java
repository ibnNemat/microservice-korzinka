package uz.nt.deliveryservice.service.mapper;

import uz.nt.deliveryservice.dto.LandmarkDto;
import uz.nt.deliveryservice.entity.Landmark;

import java.util.List;
import java.util.stream.Collectors;

public class LandmarkMap {

    public static List<LandmarkDto> toDtoList(List<Landmark> landmarks) {
        return landmarks.stream().map(LandmarkMap::toDto).collect(Collectors.toList());
    }

    public static LandmarkDto toDto(Landmark landmark) {
        return LandmarkDto.builder()
                .id(landmark.getId())
                .name(landmark.getName())
                .build();
    }

    public static Landmark toEntity(LandmarkDto landmarkDto) {
        return Landmark.builder()
                .id(landmarkDto.getId())
                .name(landmarkDto.getName())
                .cityId(landmarkDto.getCityId())
                .build();
    }
}
