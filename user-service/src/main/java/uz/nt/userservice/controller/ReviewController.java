package uz.nt.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.dto.ReviewDto;

import uz.nt.userservice.service.ReviewService;

import javax.validation.Valid;
import java.util.List;

@RestController @RequiredArgsConstructor @RequestMapping("/review")
public class ReviewController {
    private final ReviewService service;
    @PostMapping
    public void saveReview(@RequestBody @Valid ReviewDto reviewDto) {
        service.saveReview(reviewDto);
    }
    @GetMapping
    public ResponseDto<List<ReviewDto>> getAll() {
        return service.getAll();
    }
    @GetMapping("{id}")
    public ResponseDto<ReviewDto> getById(@PathVariable Integer id) {
        return service.getById(id);
    }
}
