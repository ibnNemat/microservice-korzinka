package uz.nt.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UnitDto;
import uz.nt.productservice.entity.Unit;
import uz.nt.productservice.repository.UnitRepository;
import uz.nt.productservice.service.UnitService;
import uz.nt.productservice.service.mapper.UnitMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    private final UnitMapper unitMapper;

    @Override
    public ResponseDto<Page<UnitDto>> pagination(Integer page, Integer size) {
        if(page == null || page < 0){
            return ResponseDto.<Page<UnitDto>>builder()
                    .code(-3).success(false).message("Parameter \"Page\" is null").build();
        }
        if(size == null || size <= 0){
            return ResponseDto.<Page<UnitDto>>builder()
                    .code(-3).success(false).message("Parameter \"Size\" is null").build();
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<UnitDto> data = unitRepository.findAll(pageRequest).map(unitMapper::toDto);

        return ResponseDto.<Page<UnitDto>>builder()
                .code(0).success(true).message("OK").responseData(data).build();
    }

    @Override
    public ResponseDto<UnitDto> add(UnitDto unitDto) {
        Unit entity = unitMapper.toEntity(unitDto);
        if(unitRepository.existsByName(entity.getName())){
           return ResponseDto.<UnitDto>builder()
                   .code(-2).success(false).message("Data is already exists.").build();
        }
        unitRepository.save(entity);

        return ResponseDto.<UnitDto>builder()
                .code(0).success(true).message("OK").responseData(unitMapper.toDto(entity)).build();
    }

    @Override
    public ResponseDto<UnitDto> update(UnitDto unitDto) {
        Unit newEntity = unitMapper.toEntity(unitDto);
        Optional<Unit> optional = unitRepository.findById(newEntity.getId());

        if(optional.isEmpty()){
            return ResponseDto.<UnitDto>builder()
                    .code(-4).success(false).message("Data is not exists.").build();
        }

        Unit old = optional.get();

        old.setId(newEntity.getId() == null? old.getId(): newEntity.getId());
        old.setName(newEntity.getName() == null? old.getName(): newEntity.getName());
        old.setShortName(newEntity.getShortName() == null? old.getShortName(): newEntity.getShortName());

        unitRepository.save(old);

        return ResponseDto.<UnitDto>builder()
                .code(0).success(true).message("OK").responseData(unitMapper.toDto(old)).build();
    }
}
