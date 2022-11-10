package uz.nt.deliveryservice.service.impl;

import uz.nt.deliveryservice.dto.RegionDto;
import uz.nt.deliveryservice.entity.Region;
import uz.nt.deliveryservice.repository.RegionRepository;
import uz.nt.deliveryservice.service.RegionService;
import uz.nt.deliveryservice.service.mapper.RegionMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public ResponseDto<List<RegionDto>> getAll() {
        try {
            List<Region> regions = regionRepository.findAll();

            ResourceBundle bundle = ResourceBundle.getBundle("lang");

            if (regions.isEmpty())
                return ResponseDto.<List<RegionDto>>builder().code(404).success(false).message(bundle.getString("res.not_found")).build();

            return ResponseDto.<List<RegionDto>>builder()
                    .code(200)
                    .success(true)
                    .message(bundle.getString("res.found"))
                    .responseData(regions.stream().map(RegionMap::toDtoWithOutCity).toList())
                    .build();

        } catch (Exception e) {
            log.error("Region Service GetAll method");
            return ResponseDto.<List<RegionDto>>builder().code(0).success(false).message(e.getMessage()).build();
        }
    }

    @Override
    public ResponseDto<RegionDto> getById(Integer id) {
        try {
            Optional<Region> region = regionRepository.findById(id);

            ResourceBundle bundle = ResourceBundle.getBundle("lang");

            if (region.isEmpty())
                return ResponseDto.<RegionDto>builder().code(404).success(false).message(bundle.getString("res.not_found")).build();

            return ResponseDto.<RegionDto>builder()
                    .code(200)
                    .success(true)
                    .message(bundle.getString("res.found"))
                    .responseData(RegionMap.toDtoWithOutCity(region.get()))
                    .build();

        } catch (Exception e) {
            log.error("Region Service GetById method");
            return ResponseDto.<RegionDto>builder().code(0).success(false).message(e.getMessage()).build();
        }
    }

    @Override
    public ResponseDto<RegionDto> save(RegionDto regionDto) {
        try {
            Region region = regionRepository.save(RegionMap.toEntity(regionDto));

            ResourceBundle bundle = ResourceBundle.getBundle("lang");

            return ResponseDto.<RegionDto>builder()
                    .code(200)
                    .success(true)
                    .message(bundle.getString("res.save"))
                    .responseData(RegionMap.toDtoWithOutCity(region))
                    .build();

        } catch (Exception e) {
            log.error("Region Service Save method");
            return ResponseDto.<RegionDto>builder().code(0).success(false).message(e.getMessage()).build();
        }
    }

    @Override
    public ResponseDto<RegionDto> update(RegionDto regionDto) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("lang");

            if (regionDto.getId() == null || regionDto.getId() < 1 || regionDto.getName() == null || regionDto.getName().equals(""))
                return ResponseDto.<RegionDto>builder().code(1).success(false).message(bundle.getString("res.id_null")).build();

            Optional<Region> region = regionRepository.findById(regionDto.getId());

            if (region.isEmpty())
                return ResponseDto.<RegionDto>builder().code(404).success(false).message(bundle.getString("res.not_found")).build();

            Region reg = region.get();
            reg.setName(regionDto.getName());
            reg = regionRepository.save(reg);

            return ResponseDto.<RegionDto>builder()
                    .code(200)
                    .success(true)
                    .message(bundle.getString("res.update"))
                    .responseData(RegionMap.toDtoWithOutCity(reg))
                    .build();

        } catch (Exception e) {
            log.error("Region Service Update method");
            return ResponseDto.<RegionDto>builder().code(0).success(false).message(e.getMessage()).build();
        }
    }

    @Override
    public ResponseDto<RegionDto> deleteById(Integer id) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("lang");

            if (id == null || id < 1)
                return ResponseDto.<RegionDto>builder().code(1).success(false).message(bundle.getString("res.id_null")).build();

            Optional<Region> region = regionRepository.findById(id);

            if (region.isEmpty())
                return ResponseDto.<RegionDto>builder().code(404).success(false).message(bundle.getString("res.not_found")).build();

            regionRepository.deleteById(id);

            return ResponseDto.<RegionDto>builder()
                    .code(200)
                    .success(true)
                    .message(bundle.getString("res.delete"))
                    .responseData(RegionMap.toDtoWithOutCity(region.get()))
                    .build();

        } catch (Exception e) {
            log.error("Region Service DeleteById method");
            return ResponseDto.<RegionDto>builder().code(0).success(false).message(e.getMessage()).build();
        }
    }
}
