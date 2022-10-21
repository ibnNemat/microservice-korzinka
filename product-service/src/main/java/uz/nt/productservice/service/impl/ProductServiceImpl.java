package uz.nt.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import shared.libs.dto.ProductDto;
import shared.libs.dto.ResponseDto;
import uz.nt.productservice.entity.Product;
import uz.nt.productservice.repository.ProductRepository;
import uz.nt.productservice.service.ProductService;
import uz.nt.productservice.service.mapper.ProductMapper;
import uz.nt.productservice.service.mapper.impl.ProductMapperImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ResponseDto<ProductDto> add(ProductDto productDto){
        if(productRepository.existsByName(productDto.getName())){
            return ResponseDto.<ProductDto>builder()
                    .code(-1).success(false).message("Product is all ready exists.").build();
        }
        Product entity = productMapper.toEntity(productDto);
        productRepository.save(entity);

        return ResponseDto.<ProductDto>builder()
                .code(0).success(true).message("OK").responseData(productMapper.toDto(entity)).build();
    }

    @Override
    public ResponseDto<List<ProductDto>> all(){
        List<ProductDto> list = productRepository.findAll()
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
        Page<ProductDto> page = productRepository.findAll(pageRequest).map(ProductMapperImpl::toDto);

        return ResponseDto.<Page<ProductDto>>builder()
                .code(0).success(true).message("OK").responseData(page).build();
    }

    @Override
    public ResponseDto<ProductDto> oneById(Integer id){
        Optional<Product> optional = productRepository.findById(id);
        if(optional.isPresent()){
           ProductDto productDto = optional.map(ProductMapperImpl::toDto).get();
           return ResponseDto.<ProductDto>builder()
                   .code(0).success(true).message("OK").responseData(productDto).build();
        }

        return ResponseDto.<ProductDto>builder()
                .code(-4).success(false).message("Data is not found").build();
    }

    public void delete(Integer id){
        productRepository.deleteById(id);
    }

    @Override
    public Boolean updateAmount(Integer product_id, Integer amount) {
        Integer num = productRepository.getByIdAndAmount(product_id, amount);
        if (num != null){
            productRepository.subtractProductAmount(product_id, amount);
            return true;
        }
        return false;
    }

}
