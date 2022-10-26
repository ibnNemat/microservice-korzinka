package uz.nt.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shared.libs.dto.CardTypeDto;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.entity.CardType;
import uz.nt.userservice.repository.CardTypeRepository;
import uz.nt.userservice.service.CardTypeService;
import uz.nt.userservice.service.mapper.CardTypeMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardTypeServiceImpl implements CardTypeService {
    private final CardTypeMapper cardTypeMapper;
    private final CardTypeRepository cardTypeRepository;

    @Override
    public ResponseDto<List<CardTypeDto>> getAllCardTypes() {
        List<CardTypeDto> list = cardTypeRepository.findAll().stream().map(cardTypeMapper::toDto).collect(Collectors.toList());
        return ResponseDto.<List<CardTypeDto>>builder()
                .code(0)
                .success(true)
                .message("Ok")
                .responseData(list)
                .build();
    }

    @Override
    public ResponseDto<CardTypeDto> getCardTypeById(Integer id) {
        CardType cardType = cardTypeRepository.findById(id).get();
        return ResponseDto.<CardTypeDto>builder()
                .code(0)
                .success(true)
                .message("Ok")
                .responseData(cardTypeMapper.toDto(cardType))
                .build();
    }

    @Override
    public ResponseDto deleteById(Integer id) {
        cardTypeRepository.deleteById(id);
        return ResponseDto.builder()
                .code(0)
                .success(true)
                .message("Ok")
                .build();
    }

    @Override
    public ResponseDto update(CardTypeDto cardTypeDto) {
        cardTypeRepository.save(cardTypeMapper.toEntity(cardTypeDto));
        return ResponseDto.builder()
                .code(0)
                .success(true)
                .message("Ok")
                .build();
    }

    @Override
    public ResponseDto add(CardTypeDto cardTypeDto) {
        cardTypeRepository.save(cardTypeMapper.toEntity(cardTypeDto));
        return ResponseDto.builder()
                .code(0)
                .success(true)
                .message("Ok")
                .build();
    }
}
