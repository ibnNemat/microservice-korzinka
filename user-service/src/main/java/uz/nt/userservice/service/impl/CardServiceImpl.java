package uz.nt.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.CardDto;
import uz.nt.userservice.entity.Card;
import uz.nt.userservice.repository.CardRepository;
import uz.nt.userservice.service.CardService;
import uz.nt.userservice.service.mapper.CardMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    @Override
    public ResponseDto<List<CardDto>> getAllCards() {
        List<CardDto> list = cardRepository.findAll().stream().map(cardMapper::toDto).collect(Collectors.toList());
        return ResponseDto.<List<CardDto>>builder()
                .code(0)
                .success(true)
                .message("Ok")
                .responseData(list)
                .build();
    }

    @Override
    public ResponseDto<List<CardDto>> getCardsByUserId(Integer userId) {

        return null;
    }

    @Override
    public ResponseDto<CardDto> getCardById(Integer cardId) {
        Optional<Card> card = cardRepository.findById(cardId);

//        Card card = cardRepository.findById(cardId).get();
        if (card.isPresent()) {
            return ResponseDto.<CardDto>builder()
                    .code(0)
                    .success(true)
                    .message("Ok")
                    .responseData(cardMapper.toDto(card.get()))
                    .build();
        }
        return ResponseDto.<CardDto>builder()
                .code(0)
                .success(false)
                .message("Ok")
                .build();
    }

    @Override
    public ResponseDto deleteCardById(Integer id) {
        cardRepository.deleteById(id);
        return ResponseDto.builder()
                .code(0)
                .success(true)
                .message("Ok")
                .build();
    }

    @Override
    public ResponseDto updateCard(CardDto cardDto) {
        cardRepository.save(cardMapper.toEntity(cardDto));
        return ResponseDto.builder()
                .code(0)
                .success(true)
                .message("Ok")
                .build();
    }

    @Override
    public ResponseDto addCard(CardDto cardDto) {
        cardRepository.save(cardMapper.toEntity(cardDto));
        return ResponseDto.builder()
                .code(0)
                .success(true)
                .message("Ok")
                .build();
    }
}
