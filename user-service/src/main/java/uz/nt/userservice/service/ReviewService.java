package uz.nt.userservice.service;

import shared.libs.dto.ResponseDto;
import uz.nt.userservice.dto.ReviewDto;
import uz.nt.userservice.entity.Review;

import java.util.List;

public interface ReviewService {
    void saveReview (ReviewDto reviewDto);
    ResponseDto<List<ReviewDto>> getAll();
    ResponseDto<ReviewDto> getById(Integer id);
 }
