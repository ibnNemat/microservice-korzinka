package uz.nt.userservice.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.userservice.dto.ReviewDto;
import uz.nt.userservice.entity.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewDto toDto(Review review);
    Review toEntity(ReviewDto reviewDto);
}
