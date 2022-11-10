package uz.nt.productservice.service;

import org.springframework.data.domain.Page;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UnitDto;

public interface UnitService {

    ResponseDto<Page<UnitDto>> pagination(Integer page, Integer size);

    ResponseDto<UnitDto> add(UnitDto unitDto);

    ResponseDto<UnitDto> update(UnitDto unitDto);
}
