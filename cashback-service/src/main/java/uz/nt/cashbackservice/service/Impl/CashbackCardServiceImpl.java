package uz.nt.cashbackservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shared.libs.dto.CashbackCardDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.entity.CashbackCard;
import uz.nt.cashbackservice.mapper.CashbackMapper;
import uz.nt.cashbackservice.repository.CashbackRepository;
import uz.nt.cashbackservice.service.Main.CashbackCardService;

@Service
@RequiredArgsConstructor
public class CashbackCardServiceImpl implements CashbackCardService {

    private final CashbackRepository cashbackRepository;
    private final CashbackMapper cashbackMapper;


    @Override
    public ResponseDto<Boolean> subtractUserCashback(Integer userId, Double cashback) {
        return null;
    }

    @Override
    public ResponseDto<Boolean>  increaseCashbackForUser(Integer userId, Double totalPrice) {
        return null;
    }

    @Override
    public ResponseDto<CashbackCardDto> addCashback(CashbackCardDto cashbackDto) {
        return null;
    }


    @Override
    public ResponseDto<Boolean> deleteCashBack(CashbackCardDto cashbackCardDto) {
        return null;
    }

}


