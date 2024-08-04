package com.ironhack.onlinebookstore.controller;

import com.ironhack.onlinebookstore.DTOs.ReviewDTO;
import com.ironhack.onlinebookstore.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByBookId(@PathVariable Long bookId) {
        List<ReviewDTO> reviews = reviewService.findByBookId(bookId);
        if (reviews.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@Validated @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO savedReview = reviewService.save(reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }
}