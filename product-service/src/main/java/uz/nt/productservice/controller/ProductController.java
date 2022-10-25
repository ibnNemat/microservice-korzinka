package uz.nt.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ProductDto;
import shared.libs.dto.ResponseDto;
import uz.nt.productservice.service.impl.ProductServiceImpl;


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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        productService.delete(id);
    }

}
