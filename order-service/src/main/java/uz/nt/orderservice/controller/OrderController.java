package uz.nt.orderservice.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ProductServiceExchangeDto;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.client.ProductClient;
import uz.nt.orderservice.dto.OrderDto;


import shared.libs.dto.OrderedProductsDetail;
import uz.nt.orderservice.dto.UserOrderedProducts;
import uz.nt.orderservice.service.OrderService;
import uz.nt.orderservice.dto.PaymentDetails;

import java.lang.reflect.Method;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ProductClient productClient;

    @GetMapping("/by-page")
    public ResponseDto<Page<OrderDto>> getAllOrdersByPage(@RequestParam Integer page,
                                                          @RequestParam Integer size) throws NoSuchMethodException {
        ResponseDto<Page<OrderDto>> responseDto = orderService.getAllOrdersByPage(page, size);
        Method method = OrderController.class.getMethod("getAllOrdersByPage", Integer.class, Integer.class);

        return orderService.responseDtoWithLink(page, size, method, responseDto);
    }

    @GetMapping("/{id}")
    public ResponseDto<OrderDto> getById(@PathVariable Integer id){
        return orderService.getById(id);
    }

    @PostMapping
    public ResponseDto addOrderIfNotExistUserOrders(@RequestBody List<OrderedProductsDetail> list){
        return orderService.addOrder(list);
    }

    @PutMapping
    public ResponseDto updateOrder(@RequestBody OrderDto orderDto){
        return orderService.updateOrder(orderDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteById(@PathVariable Integer id){
        return orderService.deleteById(id);
    }

    @PostMapping("/payment")
    public ResponseDto payForOrderProducts(@RequestBody PaymentDetails paymentDetails){
        return orderService.payForOrders(paymentDetails);
    }

    @GetMapping
    public ResponseDto<List<UserOrderedProducts>> getAllUsersOrderProductsIsPayedFalse(){
        return orderService.getAllUsersOrderProductsIsPayedFalse();
    }

    @GetMapping("check")
    public ProductServiceExchangeDto check(){
        return productClient.check();
    }
}
