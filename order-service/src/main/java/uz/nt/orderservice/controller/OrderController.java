package uz.nt.orderservice.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.nt.orderservice.dto.OrderDto;
import uz.nt.orderservice.dto.ResponseDto;
import uz.nt.orderservice.service.OrderService;
import java.lang.reflect.Method;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    @GetMapping("byPage")
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


}
