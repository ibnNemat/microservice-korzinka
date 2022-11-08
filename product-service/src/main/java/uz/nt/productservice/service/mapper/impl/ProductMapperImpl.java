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
                .type(ProductTypeMapperImpl.toDtoWithoutProduct(product.getType()))
                .amount(product.getAmount())
                .price(product.getPrice())
                .caption(product.getCaption())
                .active(product.getActive())
                .createdAt(product.getCreatedAt())
                .discount(DiscountMapperImpl.toDtoWithoutProduct(product.getDiscount()))
                .build();
    }

    public static ProductDto toDtoWithoutType(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .amount(product.getAmount())
                .price(product.getPrice())
                .build();
    }

    public static Product toEntityWithoutType(ProductDto productDto){
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .amount(productDto.getAmount())
                .price(productDto.getPrice())
                .build();
    }
}
