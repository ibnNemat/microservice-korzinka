package com.example.deliveryservice.service.mapper;

import com.example.deliveryservice.dto.PromoCodeDto;
import com.example.deliveryservice.entity.PromoCode;

public class PromoCodeMap {

    public static PromoCodeDto toDto(PromoCode promoCode) {
        return PromoCodeDto.builder()
                .id(promoCode.getId())
                .name(promoCode.getName())
                .startDate(promoCode.getStartDate())
                .endDate(promoCode.getEndDate())
                .build();
    }

    public static PromoCode toEntity(PromoCodeDto promoCodeDto) {
        return PromoCode.builder()
                .id(promoCodeDto.getId())
                .name(promoCodeDto.getName())
                .startDate(promoCodeDto.getStartDate())
                .endDate(promoCodeDto.getEndDate())
                .build();
    }
}
