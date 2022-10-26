package uz.nt.deliveryservice.service.impl;

import uz.nt.deliveryservice.dto.CityDto;
import uz.nt.deliveryservice.entity.City;
import uz.nt.deliveryservice.repository.CityRepository;
import uz.nt.deliveryservice.service.CityService;
import uz.nt.deliveryservice.service.mapper.CityMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;

import java.util.List;
import java.util.ResourceBundle;

@Service
@Slf4j
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public ResponseDto<List<CityDto>> getAllByRegionId(Integer id) {
        try {
            List<City> cities = cityRepository.findAllByRegionId(id);

            ResourceBundle bundle = ResourceBundle.getBundle("lang");

            if (cities.isEmpty())
                return ResponseDto.<List<CityDto>>builder().code(404).success(false).message(bundle.getString("res.not_found")).build();

            return ResponseDto.<List<CityDto>>builder()
                    .code(200)
                    .success(true)
                    .message(bundle.getString("res.found"))
                    .responseData(CityMap.toDtoList(cities))
                    .build();
            
        } catch (Exception e) {
            log.error("City Service GetAllByRegionId method");
            return ResponseDto.<List<CityDto>>builder().code(0).success(false).message(e.getMessage()).build();
        }
    }
}
