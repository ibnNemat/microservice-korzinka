package uz.nt.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import shared.libs.dto.ProductDto;
import shared.libs.dto.ResponseDto;
import uz.nt.productservice.dto.ProductSearchDto;
import uz.nt.productservice.entity.Product;
import uz.nt.productservice.repository.ProductRepository;
import uz.nt.productservice.repository.helperRepositories.ProductRepositoryHelper;
import uz.nt.productservice.service.ProductService;
import uz.nt.productservice.service.mapper.ProductMapper;
import uz.nt.productservice.service.mapper.impl.ProductMapperImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ProductRepositoryHelper productRepositoryHelper;

    public ResponseDto<ProductDto> add(ProductDto productDto){
        if(productRepository.existsByName(productDto.getName())){
            return ResponseDto.<ProductDto>builder()
                    .code(-1).success(false).message("Product is already exists.").build();
        }
        Product entity = productMapper.toEntity(productDto);
        productRepository.save(entity);

        return ResponseDto.<ProductDto>builder()
                .code(0).success(true).message("OK").responseData(productMapper.toDto(entity)).build();
    }

    @Override
    public ResponseDto<List<ProductDto>> all(){
        List<ProductDto> list = productRepository.findAllByActive(true)
                .stream().map(ProductMapperImpl::toDto).collect(Collectors.toList());

        return list.size() == 0?
                ResponseDto.<List<ProductDto>>builder()
                        .code(-2).success(false).message("List is empty.").build():
                ResponseDto.<List<ProductDto>>builder()
                        .code(0).success(true).message("OK").responseData(list).build();
    }

    @Override
    public ResponseDto<Page<ProductDto>> pagination(Integer p, Integer s){
        if(p == null || p < 0){
            return ResponseDto.<Page<ProductDto>>builder()
                    .code(-3).success(false).message("Page is null or below zero.").build();
        }
        if(s == null || s < 1){
            return ResponseDto.<Page<ProductDto>>builder()
                    .code(-3).success(false).message("Size is null or below zero.").build();
        }

        PageRequest pageRequest = PageRequest.of(p, s);
        Page<ProductDto> page = productRepository.findAllByActiveIsTrue(pageRequest).map(ProductMapperImpl::toDto);

        return ResponseDto.<Page<ProductDto>>builder()
                .code(0).success(true).message("OK").responseData(page).build();
    }

    @Override
    public ResponseDto<ProductDto> oneById(Integer id){
        Optional<Product> optional = productRepository.findByIdAndActive(id, true);
        if(optional.isPresent()){
           ProductDto productDto = optional.map(ProductMapperImpl::toDto).get();
           return ResponseDto.<ProductDto>builder()
                   .code(0).success(true).message("OK").responseData(productDto).build();
        }

        return ResponseDto.<ProductDto>builder()
                .code(-4).success(false).message("Data is not found").build();
    }

    @Override
    public void delete(Integer id){
        productRepository.deleteById(id);
    }

    @Override
    public ResponseDto<Page<ProductDto>> search(MultiValueMap<String, String> map, ProductSearchDto dto) {
        if(!map.containsKey("page") || map.getFirst("page") == null){
            return ResponseDto.<Page<ProductDto>>builder()
                    .code(-3).success(false).message("Parameter \"Page\" is null!").build();
        }
        if(!map.containsKey("size") || map.getFirst("size") == null){
            return ResponseDto.<Page<ProductDto>>builder()
                    .code(-3).success(false).message("Parameter \"Size\" is null!").build();
        }

        int page = Integer.parseInt(map.getFirst("page"));
        int size = Integer.parseInt(map.getFirst("size"));

        if(page < 0 || size <= 0){
            return ResponseDto.<Page<ProductDto>>builder()
                    .code(-4).success(false).message("Parameters of \"Page\" or \"Size\" are invalid").build();
        }

        Map<String, Object> results = productRepositoryHelper.searchByParams(map, dto);

        List<ProductDto> list = (List<ProductDto>) results.get("Data");
        Integer count = (Integer) results.get("Count");

        PageImpl<ProductDto> pagination = new PageImpl<ProductDto>(list, PageRequest.of(page, size), count);

        return ResponseDto.<Page<ProductDto>>builder()
                .code(0).success(true).message("OK").responseData(pagination).build();
    }

    @Override
    public ResponseDto<Boolean> updateAmount(Integer product_id, Double amount) {
        Boolean exists = productRepository.existsByIdAndAmountGreaterThan(product_id, amount);
        if (exists){
            productRepository.subtractProductAmount(amount, product_id);
            return ResponseDto.<Boolean>builder().success(true).message("OK").responseData(true).build();
        }
        return ResponseDto.<Boolean>builder()
                .success(false)
                .message("There is no any product with this id or amount of product is less than required")
                .responseData(false).build();
    }

    @Override
    public ResponseDto<Boolean> checkAmount(Integer productId, Double amount) {
        if(productId != null && productId > 0){
            return ResponseDto.<Boolean>builder()
                    .code(-5).success(false).message("Product id is null or equal of zero.").build();
        }
        if(amount != null && amount > 0){
            return ResponseDto.<Boolean>builder()
                    .code(-5).success(false).message("Amount is null or equal of zero").build();
        }

        boolean result = productRepository.existsByIdAndAmountGreaterThan(productId, amount);

        return ResponseDto.<Boolean>builder()
                .code(0).success(true).message("OK").responseData(result).build();
    }

    @Override
    public ResponseDto<Map<Integer, ProductDto>> getProductsByList(List<Integer> ids) {
        if(ids == null || ids.size() == 0){
            return ResponseDto.<Map<Integer, ProductDto>>builder()
                    .code(-6).success(false).message("Type of \"Request body\" is not instance of \"List\"").build();
        }

        List<Product> products = productRepository.findByIdIn(ids);
        Map<Integer, ProductDto> data = new HashMap<>();

        products.stream()
                .map(ProductMapperImpl::toDto)
                .forEach(p -> {
                    data.put(p.getId(), p);
                });

        return ResponseDto.<Map<Integer, ProductDto>>builder()
                .code(0).success(true).message("OK").responseData(data).build();
    }
}
