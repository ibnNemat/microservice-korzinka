package uz.nt.productservice.service.mapper.impl;

import shared.libs.dto.ProductDto;
import uz.nt.productservice.entity.Product;

public class ProductMapperImpl {

    public static Product toEntity(ProductDto productDto){
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .amount(productDto.getAmount())
                .price(productDto.getPrice())
                .type(ProductTypeMapperImpl.toEntityWithoutProduct(productDto.getType()))
                .build();
    }

    public static ProductDto toDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .amount(product.getAmount())
                .price(product.getPrice())
                .type(ProductTypeMapperImpl.toDtoWithoutProduct(product.getType()))
                .build();
    }
}
