package uz.nt.orderservice.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.orderservice.dto.PaymentHistoryDto;
import uz.nt.orderservice.entity.PaymentHistory;

@Mapper(componentModel = "spring")
public interface PaymentHistoryMapper {
    PaymentHistory toEntity(PaymentHistoryDto paymentHistoryDto);
    PaymentHistoryDto toDto(PaymentHistory paymentHistory);
}
