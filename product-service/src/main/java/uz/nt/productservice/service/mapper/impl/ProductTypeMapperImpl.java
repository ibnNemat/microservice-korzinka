package uz.nt.productservice.service.mapper.impl;

import shared.libs.dto.ProductTypeDto;
import shared.libs.dto.UnitDto;
import uz.nt.productservice.entity.ProductType;
import uz.nt.productservice.entity.Unit;

public class ProductTypeMapperImpl {

    public static ProductType toEntityWithoutProduct(ProductTypeDto dto){
        return ProductType.builder()
                .id(dto.getId())
                .barcode(dto.getBarcode())
                .name(dto.getName())
                .unit(Unit.builder()
                        .id(dto.getUnit().getId())
                        .name(dto.getUnit().getName())
                        .short_name(dto.getUnit().getShort_name())
                        .build())
                .products(null)
                .build();
    }

    public static ProductTypeDto toDtoWithoutProduct(ProductType entity){
        return ProductTypeDto.builder()
                .id(entity.getId())
                .barcode(entity.getBarcode())
                .name(entity.getName())
                .unit(UnitDto.builder()
                        .id(entity.getUnit().getId())
                        .name(entity.getUnit().getName())
                        .short_name(entity.getUnit().getShort_name())
                        .build())
                .products(null)
                .build();
    }
}
