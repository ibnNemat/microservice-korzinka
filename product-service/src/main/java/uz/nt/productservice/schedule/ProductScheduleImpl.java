package uz.nt.productservice.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import shared.libs.dto.ProductDto;
import shared.libs.dto.ResponseDto;
import uz.nt.productservice.entity.Product;
import uz.nt.productservice.repository.helperRepositories.ProductRepositoryHelper;
import uz.nt.productservice.service.mapper.impl.ProductMapperImpl;

import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ProductScheduleImpl implements ProductSchedule{

    private final ProductRepositoryHelper productRepositoryHelper;

    /*
    CRON stars definition

    1 - minute
    2 - hour
    3 - day of month
    4 - month
    5 - day of week
    6 - command
     */
    @Scheduled(cron = "30 30 23 * * *")
    @Override
    public ResponseDto<List<ProductDto>> getTopProductsThatOrderALot() {
        List<Product> productList = productRepositoryHelper.getTopOrderedProducts();

        List<ProductDto> productDtoList = productList.stream().map(ProductMapperImpl::toDto).toList();

        return ResponseDto.<List<ProductDto>>builder()
                .code(200)
                .success(true)
                .message("OK")
                .responseData(productDtoList)
                .build();
    }

//    @Scheduled(cron = "0 3 * * * *")
//    @Override
//    public void updateProductWhereAmountIsZero() {
//        productRepositoryHelper.updateProduct();
//
//        log.info(
//                "Schedule is worked. Thread name: {} Time: {} Work task: Product's \"field is_active\" = false if product's amount is below 0(zero) or storage life is invalid. ",
//                Thread.currentThread().getName(),System.currentTimeMillis());
//    }


}
