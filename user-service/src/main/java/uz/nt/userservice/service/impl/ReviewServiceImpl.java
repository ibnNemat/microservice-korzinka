package uz.nt.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.dto.ReviewDto;
import uz.nt.userservice.entity.Review;
import uz.nt.userservice.repository.ReviewRepository;
import uz.nt.userservice.service.ReviewService;
import uz.nt.userservice.service.mapper.ReviewMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final ReviewMapper mapper;


    @Override
    public void saveReview(ReviewDto reviewDto) {
        repository.save(mapper.toEntity(reviewDto));
    }

    @Override
    public ResponseDto<List<ReviewDto>> getAll() {
        List<Review> reviews = repository.findAll();
        if (reviews.size() != 0) {
            List<ReviewDto> reviewDtoList = reviews.stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());
            return ResponseDto.<List<ReviewDto>>builder().
                    code(100).message("Success")
                    .success(true)
                    .responseData(reviewDtoList).build();
        }
        return ResponseDto.<List<ReviewDto>>builder().
                code(-100).message("Failed")
                .success(false)
                .build();
    }

    @Override
    public ResponseDto<ReviewDto> getById(Integer id) {
        Optional<Review> optionalReview = repository.findById(id);
        if (optionalReview.isPresent()) {
            return ResponseDto.<ReviewDto>builder()
                    .code(0)
                    .message("Success")
                    .success(true)
                    .responseData(mapper.toDto(optionalReview.get()))
                    .build();
        }
        return ResponseDto.<ReviewDto>builder()
                .code(-100)
                .message("Failed")
                .success(false)
                .build();
    }
}
