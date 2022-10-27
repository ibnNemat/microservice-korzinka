package uz.nt.orderservice.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.dto.OrderProductsDto;
import uz.nt.orderservice.service.OrderProductsService;

import java.lang.reflect.Method;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-products")
public class OrderProductsController {
    private final OrderProductsService orderProductsService;

    @GetMapping("/by-page")
    public ResponseDto<Page<OrderProductsDto>> getAllOrderProductsByPage(@RequestParam Integer page,
                                                          @RequestParam Integer size) throws NoSuchMethodException {
        ResponseDto<Page<OrderProductsDto>> responseDto = orderProductsService
                .getAllOrderProductsByPage(page, size);
        Method method = OrderProductsController.class.getMethod(
                "getAllOrderProductsByPage", Integer.class, Integer.class);

        return orderProductsService.responseDtoWithLink(page, size, method, responseDto);
    }

    @GetMapping("/{id}")
    public ResponseDto<OrderProductsDto> getById(@PathVariable Integer id){
        return orderProductsService.getById(id);
    }

    @GetMapping("/sum-orders-monthly")
    public ResponseDto<HashMap<Integer, Double>> sumAllOrderedProductsMonthly(){
        return orderProductsService.quantityOrderedProductsPerMonth();
    }


    @GetMapping("/sum-orders-quarterly")
    public ResponseDto<HashMap<Integer, Double>> sumAllOrderedProductsPerQuarterly(){
        return orderProductsService.quantityOrderedProductsPerQuarter();
    }

    @PutMapping("/update-order")
    public ResponseDto updateOrderProducts(@RequestBody OrderProductsDto orderProductsDto){
        return orderProductsService.updateOrderProducts(orderProductsDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteById(@PathVariable Integer id){
        return orderProductsService.deleteById(id);
    }

    @DeleteMapping("/delete-order/{orderId}")
    public ResponseDto deleteByOrderId(@PathVariable Integer orderId){
        return orderProductsService.deleteByOrderId(orderId);
    }

    @DeleteMapping("/delete-product-and-order")
    private ResponseDto deleteByProductIdAndOrderId(
            @RequestParam Integer orderId,
            @RequestParam Integer productId){
        return orderProductsService.deleteByProductIdAndOrderId(orderId, productId);
    }

}
