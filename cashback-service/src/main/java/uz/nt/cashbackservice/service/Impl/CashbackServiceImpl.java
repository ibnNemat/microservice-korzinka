package uz.nt.cashbackservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shared.libs.dto.CashbackDto;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserDto;
import uz.nt.cashbackservice.entity.Cashback;
import uz.nt.cashbackservice.mapper.CashbackMapper;
import uz.nt.cashbackservice.repository.CashbackRepository;
import uz.nt.cashbackservice.service.Main.CashbackService;

@Service
@RequiredArgsConstructor
public class CashbackServiceImpl implements CashbackService{

    private CashbackRepository cashbackRepository;
    private CashbackMapper cashbackMapper;


    @Override
    public void subtractUserCashback(Integer userId, Double cashback) {

    }

    @Override
    public void calculateCashbackForUser(Integer userId, Double totalPrice) {

    }

    @Override
    public ResponseDto<CashbackDto> getCashbackByUserId(Integer userId) {
        return null;
    }

    @Override
    public ResponseDto<CashbackDto> addCashback(CashbackDto cashbackDto) {
        return null;
    }

    @Override
    @Transactional
    public ResponseDto<CashbackDto> add() {
        int userId;
        if (SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getDetails() instanceof  UserDto userDto){
            userId = userDto.getId();

            Cashback cashback = Cashback.builder().amount(5000D).barcode(RandomStringUtils.random(16)).userId(userId).build();
            cashbackRepository.save(cashback);

            CashbackDto cashbackDto = cashbackMapper.toDto(cashback);

            return ResponseDto.<CashbackDto>builder().success(true).message("OK").responseData(cashbackDto).build();
        } else {
            return ResponseDto.<CashbackDto>builder().success(false).message("NO").build();
        }

    }
}


