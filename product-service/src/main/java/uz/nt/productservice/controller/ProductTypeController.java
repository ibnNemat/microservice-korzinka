package uz.nt.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ProductTypeDto;
import shared.libs.dto.ResponseDto;
import uz.nt.productservice.service.ProductTypeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product-type")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @PostMapping
    public ResponseDto<ProductTypeDto> add(@RequestBody ProductTypeDto productTypeDto){
        return productTypeService.add(productTypeDto);
    }

//    @GetMapping
//    public ResponseDto<List<ProductTypeDto>> all(){
//        return productTypeService.all();
//    }

    @GetMapping("/pagination")
    public ResponseDto<Page<ProductTypeDto>> pagination(@RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size){
        return productTypeService.pagination(page, size);
    }

    @GetMapping("/{id}")
    public ResponseDto<ProductTypeDto> oneById(@PathVariable Integer id){
        return productTypeService.oneById(id);
    }

    @GetMapping("/main-categories")
    public ResponseDto<Page<ProductTypeDto>> mainCategories(@RequestParam(required = false) Integer page,
                                                            @RequestParam(required = false) Integer size){
        return productTypeService.mainCategories(page, size);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        productTypeService.delete(id);
    }
}
