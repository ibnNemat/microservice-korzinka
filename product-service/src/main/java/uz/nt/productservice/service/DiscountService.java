package uz.nt.productservice.service;

import org.springframework.data.domain.Page;
import shared.libs.dto.DiscountDto;
import shared.libs.dto.ResponseDto;

public interface DiscountService {

    ResponseDto<DiscountDto> add(DiscountDto discountDto);

    ResponseDto<Page<DiscountDto>> pagination(Integer page, Integer size);

    ResponseDto<DiscountDto> update(DiscountDto discountDto);
}
