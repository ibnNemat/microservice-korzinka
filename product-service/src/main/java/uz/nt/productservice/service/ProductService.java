package uz.nt.productservice.service;

import org.springframework.data.domain.Page;
import shared.libs.dto.ProductDto;
import shared.libs.dto.ResponseDto;

import java.util.List;

public interface ProductService {

    ResponseDto<ProductDto> add(ProductDto productDto);

    ResponseDto<List<ProductDto>> all();

    ResponseDto<Page<ProductDto>> pagination(Integer page, Integer size);

    ResponseDto<ProductDto> oneById(Integer id);

    void delete(Integer id);

    Boolean updateAmount(Integer product_id, Integer amount);
}
