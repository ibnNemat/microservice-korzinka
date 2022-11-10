package uz.nt.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ProductDto;
import shared.libs.dto.ResponseDto;
import uz.nt.productservice.dto.ProductSearchDto;
import uz.nt.productservice.service.impl.ProductServiceImpl;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping
    public ResponseDto<ProductDto> add(@RequestBody ProductDto productDto){
        return productService.add(productDto);
    }

    @GetMapping("/pagination")
    public ResponseDto<Page<ProductDto>> pagination(@RequestParam(required = false) Integer page,
                                                    @RequestParam(required = false) Integer size
                                                    ){
        return productService.pagination(page, size);
    }

    @GetMapping("/{id}")
    public ResponseDto<ProductDto> one(@PathVariable Integer id){
        return productService.oneById(id);
    }

    @GetMapping("/by-params")
    public ResponseDto<Page<ProductDto>> filter(@RequestParam MultiValueMap<String, String> map,
                                                @RequestBody ProductSearchDto productSearchDto){
        return productService.search(map, productSearchDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        productService.delete(id);
    }

    @PostMapping("/update-amount")
    public ResponseDto<Boolean> update(@RequestParam Integer productId, @RequestParam Double amount){
        return productService.updateAmount(productId, amount);
    }

    @GetMapping("/check-amount")
    public ResponseDto<Boolean> checkAmount(@RequestParam(required = false) Integer productId,
                                            @RequestParam(required = false) Double amount){
        return productService.checkAmount(productId, amount);
    }

    @PostMapping("/products-by-id")
    public ResponseDto<Map<Integer, ProductDto>> getShownDtoList(@RequestBody List<Integer> ids){
        return productService.getProductsByList(ids);
    }
}
