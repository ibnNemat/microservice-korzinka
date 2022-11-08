package uz.nt.productservice.service.mapper.impl;

import shared.libs.dto.DiscountDto;
import uz.nt.productservice.entity.Discount;

public class DiscountMapperImpl {

    public static Discount toEntity(DiscountDto dto){
        return Discount.builder()
                .id(dto.getId())
                .discount(dto.getDiscount())
                .start(dto.getStart())
                .finish(dto.getFinish())
                .status(dto.getStatus())
                .product(ProductMapperImpl.toEntityWithoutType(dto.getProduct()))
                .build();
    }

    public static DiscountDto toDto(Discount discount){
        return DiscountDto.builder()
                .id(discount.getId())
                .discount(discount.getDiscount())
                .start(discount.getStart())
                .finish(discount.getFinish())
                .status(discount.getStatus())
                .product(ProductMapperImpl.toDtoWithoutType(discount.getProduct()))
                .build();
    }

    public static DiscountDto toDtoWithoutProduct(Discount discount){
        return DiscountDto.builder()
                .id(discount.getId())
                .discount(discount.getDiscount())
                .start(discount.getStart())
                .finish(discount.getFinish())
                .status(discount.getStatus())
                .build();
    }
}
