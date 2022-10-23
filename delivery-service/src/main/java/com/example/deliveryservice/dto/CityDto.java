package com.example.deliveryservice.dto;

import com.example.deliveryservice.entity.Landmark;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityDto {
    private Integer id;
    private String name;
    private Integer regionId;
    private List<LandmarkDto> landmarks;
}
