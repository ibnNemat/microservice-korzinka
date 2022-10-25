package uz.nt.deliveryservice.service.mapper;

import uz.nt.deliveryservice.dto.CityDto;
import uz.nt.deliveryservice.entity.City;

import java.util.List;
import java.util.stream.Collectors;

public class CityMap {

    public static List<CityDto> toDtoList(List<City> cities) {
        return cities.stream().map(CityMap::toDtoWithOutLandmark).collect(Collectors.toList());
    }

    public static CityDto toDtoWithOutLandmark(City city) {
        return CityDto.builder()
                .id(city.getId())
                .name(city.getName())
                .build();
    }

    public static CityDto toDto(City city) {
        return CityDto.builder()
                .id(city.getId())
                .name(city.getName())
                .landmarks(LandmarkMap.toDtoList(city.getLandmarks()))
                .build();
    }

    public static City toEntity(CityDto cityDto) {
        return City.builder()
                .id(cityDto.getId())
                .name(cityDto.getName())
                .regionId(cityDto.getRegionId())
                .build();
    }
}
