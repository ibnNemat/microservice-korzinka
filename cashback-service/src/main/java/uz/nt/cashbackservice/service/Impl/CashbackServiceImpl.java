package uz.nt.cashbackservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import shared.libs.dto.CashbackDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.Entity.Cashback;
import uz.nt.cashbackservice.mapper.CashbackMapper;
import uz.nt.cashbackservice.repository.CashbackRepository;
import uz.nt.cashbackservice.service.Main.CashbackService;

@Service
@RequiredArgsConstructor
public class CashbackServiceImpl implements CashbackService {

    private final CashbackRepository cashbackRepository;
    private final CashbackMapper cashbackMapper;

    @Override
    public void subtractUserCashback(Integer userId, Double cashbackAmount) {

        Cashback cashback = cashbackRepository.findByUserId(userId);
        cashback.setAmount(cashback.getAmount() - cashbackAmount);
        cashbackRepository.save(cashback);

    }

    @Override
    public void calculateCashbackForUser(Integer userId, Double totalPrice) {
        Cashback cashback = cashbackRepository.findByUserId(userId);
        if(totalPrice >= 1000_000.0 && cashback.getPercent() == 1){
            cashback.setPercent(2);
            cashbackRepository.save(cashback);
        }
        if(totalPrice >= 3000_000 && cashback.getPercent() == 2){
            cashback.setUserId(3);
            cashbackRepository.save(cashback);
        }
    }

    @Override
    public ResponseDto<CashbackDto> getCashbackByUserId(Integer userId) {
        CashbackDto cashbackDto = cashbackMapper.toDto(cashbackRepository.findByUserId(userId));
        return ResponseDto.<CashbackDto>builder()
                .code(200)
                .message("Ok")
                .success(true)
                .responseData(cashbackDto)
                .build();

    }

    @Override
    public ResponseDto<CashbackDto> addCashback(CashbackDto cashbackDto) {
        Cashback cashback = cashbackMapper.toEntity(cashbackDto);
        cashbackDto = cashbackMapper.toDto(cashbackRepository.save(cashback));
        return ResponseDto.<CashbackDto>builder()
                .code(200)
                .message("Ok")
                .success(true)
                .responseData(cashbackDto)
                .build();
    }

}


