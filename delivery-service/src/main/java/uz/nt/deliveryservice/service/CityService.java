package uz.nt.deliveryservice.service;

import uz.nt.deliveryservice.dto.CityDto;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;

import java.util.List;

@Service
public interface CityService {

    ResponseDto<List<CityDto>> getAllByRegionId(Integer id);
}
