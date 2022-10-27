package uz.nt.cashbackservice.service.Main;

import shared.libs.dto.CashbackHistoryDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.entity.CashbackHistory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface CashbackHistoryService {

    void addCashbackHistory(CashbackHistory cashbackHistory);

    ResponseDto<Boolean> deleteCashbackHistoryByCardId(Integer cashbackCardId, HttpServletRequest request);

    ResponseDto<List<CashbackHistoryDto>> getCashbackHistoryByCardId(Integer cashbackCardId, HttpServletRequest request);

    ResponseDto<List<CashbackHistoryDto>> getCashbackHistoryBetween(Date date, HttpServletRequest request);

}
