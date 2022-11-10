package uz.nt.productservice.schedule;

import shared.libs.dto.ProductDto;
import shared.libs.dto.ResponseDto;

import java.util.List;

public interface ProductSchedule {

    ResponseDto<List<ProductDto>> getTopProductsThatOrderALot();

//    void updateProductWhereAmountIsZero();
}
