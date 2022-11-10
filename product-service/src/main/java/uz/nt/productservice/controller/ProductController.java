package uz.nt.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.OrderedProductsDetail;
import shared.libs.dto.ProductDto;
import shared.libs.dto.ProductServiceExchangeDto;
import shared.libs.dto.ResponseDto;
import uz.nt.productservice.dto.ProductSearchDto;
import uz.nt.productservice.service.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseDto<ProductDto> one(@PathVariable Integer id, HttpServletRequest request){

        System.out.println("LANGUAGE: " + request.getLocale().getLanguage());
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

    @GetMapping("/discounted-products")
    public ResponseDto<Page<ProductDto>> discountProducts(@RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer size){
        return productService.discountProducts(page, size);
    }

    @PostMapping("/set-amount")
    public ResponseDto setAmount(@RequestParam(required = false) Double amount,
                                 @RequestParam(required = false) Integer productId){
        return productService.setAmount(amount, productId);
    }

    @PutMapping("/update")
    public ResponseDto<ProductDto> update(@RequestBody ProductDto productDto){
        return productService.update(productDto);
    }

    @PostMapping("/rollback-product-amount")
    public void rollback(@RequestBody List<OrderedProductsDetail> orderedProducts){
        productService.rollbackProductsAmount(orderedProducts);
    }

    @GetMapping("check")
    public ProductServiceExchangeDto check(){
        return new ProductServiceExchangeDto("text", System.getProperty("server.port"));
    }
}
