package uz.nt.cashbackservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import shared.libs.dto.ResponseDto;

@FeignClient(value = "")
public interface CashbackFeign {

}
