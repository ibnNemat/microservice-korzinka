package uz.nt.deliveryservice.service.mapper;

import uz.nt.deliveryservice.dto.RegionDto;
import uz.nt.deliveryservice.entity.Region;

public class RegionMap {

    public static RegionDto toDtoWithOutCity(Region region) {
        return RegionDto.builder()
                .id(region.getId())
                .name(region.getName())
                .build();
    }

    public static RegionDto toDto(Region region) {
        return RegionDto.builder()
                .id(region.getId())
                .name(region.getName())
                .cities(CityMap.toDtoList(region.getCities()))
                .build();
    }

    public static Region toEntity(RegionDto regionDto) {
        return Region.builder()
                .id(regionDto.getId())
                .name(regionDto.getName())
                .build();
    }
}
