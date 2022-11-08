package uz.nt.productservice.service;

import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import shared.libs.dto.OrderedProductsDetail;
import shared.libs.dto.ProductDto;
import shared.libs.dto.ResponseDto;
import uz.nt.productservice.dto.ProductSearchDto;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ResponseDto<ProductDto> add(ProductDto productDto);

    ResponseDto<List<ProductDto>> all();

    ResponseDto<Page<ProductDto>> pagination(Integer page, Integer size);

    ResponseDto<ProductDto> oneById(Integer id);

    void delete(Integer id);

    ResponseDto<Page<ProductDto>> search(MultiValueMap<String, String> map,  ProductSearchDto dto);

    ResponseDto<Boolean> updateAmount(Integer product_id, Double amount);

    ResponseDto<Boolean> checkAmount(Integer productId, Double amount);

    ResponseDto<Map<Integer, ProductDto>> getProductsByList(List<Integer> ids);

    ResponseDto<Page<ProductDto>> discountProducts(Integer page, Integer size);

    ResponseDto<Object> setAmount(Double amount, Integer productId);

    ResponseDto<ProductDto> update(ProductDto dto);

    void rollbackProductsAmount(List<OrderedProductsDetail> orderedProducts);
}
