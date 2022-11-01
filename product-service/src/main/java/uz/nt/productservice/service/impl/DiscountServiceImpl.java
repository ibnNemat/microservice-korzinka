package uz.nt.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import shared.libs.dto.DiscountDto;
import shared.libs.dto.ResponseDto;
import uz.nt.productservice.entity.Discount;
import uz.nt.productservice.errors.exceptions.DataExceptions;
import uz.nt.productservice.errors.exceptions.PaginationExceptions;
import uz.nt.productservice.repository.DiscountRepository;
import uz.nt.productservice.service.DiscountService;
import uz.nt.productservice.service.mapper.DiscountMapper;
import uz.nt.productservice.service.mapper.DiscountMapperImpl;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository repository;

    private final DiscountMapper mapper;

    private final DiscountMapperImpl mapperImpl;

    @Override
    public ResponseDto<DiscountDto> add(DiscountDto discountDto) {
        if(repository.
                existsByDiscountAndStartAndFinish(discountDto.getDiscount(), discountDto.getStart(), discountDto.getFinish())){
            throw DataExceptions.enable();
        }
        Discount entity = mapper.toEntity(discountDto);
        repository.save(entity);

        // Discount mapper implement yozish kere. GET qiganda "Stack overflow" ga tushmasligi uchun
        return ResponseDto.<DiscountDto>builder()
                .code(0).success(true).message("OK").build();
    }

    @Override
    public ResponseDto<Page<DiscountDto>> pagination(Integer page, Integer size) {
        if(page == null || page < 0){
            throw PaginationExceptions.getPage();
        }
        if(size == null || size <= 0){
            throw PaginationExceptions.getSize();
        }


        PageRequest pageRequest = PageRequest.of(page, size);
        Page<DiscountDto> pagination = repository.findAllByStatusTrue(pageRequest).map(mapperImpl::toDto);

        return ResponseDto.<Page<DiscountDto>>builder()
                .code(0).success(true).message("OK").responseData(pagination).build();
    }

    @Override
    public ResponseDto<DiscountDto> update(DiscountDto discountDto) {
        Optional<Discount> optional = repository.findById(discountDto.getId());
        if(optional.isEmpty()){
            throw DataExceptions.disable();
        }

        // Discount mapper implement classini toDto metodini ishlatish kere.

        Discount old = optional.get();
        Discount discount = new Discount();
        Discount updated = mapper.toEntity(discountDto);

        discount.setId(updated.getId() == null? old.getId(): updated.getId());
        discount.setDiscount(updated.getDiscount() == null? old.getDiscount(): updated.getDiscount());
        discount.setStart(updated.getStart() == null? old.getStart(): updated.getStart());
        discount.setFinish(updated.getFinish() == null? old.getFinish(): updated.getFinish());
        discount.setStatus(updated.getStatus() == null? old.getStatus(): false);
        discount.setProduct(updated.getProduct() == null? old.getProduct(): updated.getProduct());

        repository.save(discount);
        return ResponseDto.<DiscountDto>builder()
                .code(0).success(true).message("OK").responseData(mapperImpl.toDto(discount)).build();
    }
}
