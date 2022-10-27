package uz.nt.orderservice.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import shared.libs.utils.MyDateUtil;
import uz.nt.orderservice.client.ProductClient;
import uz.nt.orderservice.dto.OrderProductsDto;
import uz.nt.orderservice.dto.OrderedProductsDetail;
import uz.nt.orderservice.entity.OrderProducts;
import uz.nt.orderservice.repository.OrderProductsRepository;
import uz.nt.orderservice.repository.helperRepository.OrderProductRepositoryHelper;
import uz.nt.orderservice.service.OrderProductsService;
import uz.nt.orderservice.service.mapper.OrderProductsMapper;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProductsServiceImpl implements OrderProductsService {
    private final OrderProductsRepository orderProductsRepository;
    private final OrderProductsMapper orderProductsMapper;
    private final ProductClient productClient;
    private final OrderProductRepositoryHelper orderProductRepositoryHelper;
    @Override
    public ResponseDto addOrderProducts(Integer order_id, Integer product_id, Double amount) {
        try {
            ResponseDto<Boolean> checkProductAmount = productClient.checkAmountProduct(product_id, amount);
            if (!checkProductAmount.getSuccess() || !checkProductAmount.getResponseData()){
                return ResponseDto.builder()
                        .code(-5)
                        .message("We don't have products in that many amounts!")
                        .build();
            }
            return saveOrUpdateOrderProduct(product_id, order_id, amount);
        }catch (Exception e){
            return ResponseDto.builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }

    private ResponseDto saveOrUpdateOrderProduct(Integer product_id, Integer order_id, Double amount) {
        Optional<OrderProducts> optional = orderProductsRepository
                .findByOrderIdAndProductId(order_id, product_id);

        OrderProducts orderProduct;
        if (optional.isPresent() && optional.get().getAmount()!= null){
            Integer orderedProductId = optional.get().getId();
            amount += optional.get().getAmount();
            orderProduct = new OrderProducts(orderedProductId, order_id, product_id, amount);
        }else {
            orderProduct = new OrderProducts(null, order_id, product_id, amount);
        }
        orderProductsRepository.save(orderProduct);

        return ResponseDto.builder()
                .code(200)
                .success(true)
                .message("Successfully saved")
                .build();
    }

    @Override
    public ResponseDto<OrderProductsDto> getById(Integer id) {
        return null;
    }

    @Override
    public ResponseDto<Page<OrderProductsDto>> getAllOrderProductsByPage(Integer page, Integer size) {
        try {
            if (page == null || size == null) {
                return ResponseDto.<Page<OrderProductsDto>>builder()
                        .code(-100)
                        .message("Page or size is null")
                        .build();
            }
            PageRequest pageRequest = PageRequest.of(page, size);

            Page<OrderProductsDto> productDtoList = orderProductsRepository.findAll(pageRequest)
                    .map(orderProductsMapper::toDto);

            return ResponseDto.<Page<OrderProductsDto>>builder()
                    .code(200)
                    .success(true)
                    .message("OK")
                    .responseData(productDtoList)
                    .build();

        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<Page<OrderProductsDto>>builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto updateOrderProducts(OrderProductsDto orderProductsDto) {
        return null;
    }

    @Override
    public ResponseDto deleteById(Integer id) {
        return null;
    }

    @Override
    public ResponseDto deleteByOrderId(Integer orderId) {
        return null;
    }

    @Override
    public ResponseDto deleteByProductIdAndOrderId(Integer orderId, Integer productId) {
        return null;
    }

    public String buildStringDate(Integer monthlyOrQuarterly) throws Exception {
        Date date = new Date();
        LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
        Integer day = localDate.getDayOfMonth();
        Integer month = localDate.getMonth().getValue();
        Integer year = localDate.getYear();

        String stringDate;
        if (monthlyOrQuarterly == 1){
            if (month == 1){
                year -= year;
                month = 12;
            }else {
                month -= 1;
            }
        }else{
            if (month < 4){
                month += 9;
                year -=year;
            }
        }
        stringDate = year + "-" + month + "-" + day;

        Date date1 = MyDateUtil.parseToDate(stringDate);

        java.sql.Date lastMonth = new java.sql.Date(date1.getTime());
        java.sql.Date currentDate = new java.sql.Date(date.getTime());

    }

    @Override
    public ResponseDto<HashMap<Integer, Double>> quantityOrderedProductsPerMonth() {
        try{
            Date date = new Date();
            String stringDate = MyDateUtil.parseToString(date);
            if (stringDate == null){
                return ResponseDto.<HashMap<Integer, Double>>builder()
                        .code(500)
                        .message("Error while parsingDate")
                        .build();
            }


            if (stringDate.charAt(5) == '0' && stringDate.charAt(5) == '1'){
                Month month = Month.OCTOBER;
                month.getValue();
            }

            return null;
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<HashMap<Integer, Double>>builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<HashMap<Integer, Double>> quantityOrderedProductsPerQuarter() {
        Date date = new Date();
        return null;
    }

    @Override
    public List<OrderedProductsDetail> getOrderedProductsToPayFor(Integer order_id) {
        List<OrderedProductsDetail> orderedProductDetails = orderProductRepositoryHelper.getOrderedProductDetails(order_id);
        return orderedProductDetails;
    }

    @Override
    public ResponseDto<Page<OrderProductsDto>> responseDtoWithLink(Integer page, Integer size, Method method, ResponseDto<Page<OrderProductsDto>> responseDto) {
        try {
            Map<String, Integer> mapParams = new HashMap<>();
            mapParams.put("page", page+1);
            mapParams.put("size", size);

            Link link = WebMvcLinkBuilder.linkTo(method)
                    .withRel(IanaLinkRelations.NEXT)
                    .expand(mapParams);
            return responseDto.add(link);

        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<Page<OrderProductsDto>>builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }
}
