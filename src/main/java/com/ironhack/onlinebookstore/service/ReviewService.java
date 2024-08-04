package com.ironhack.onlinebookstore.service;

import com.ironhack.onlinebookstore.DTOs.ReviewDTO;
import com.ironhack.onlinebookstore.model.Book;
import com.ironhack.onlinebookstore.model.Review;
import com.ironhack.onlinebookstore.model.User;
import com.ironhack.onlinebookstore.repository.BookRepository;
import com.ironhack.onlinebookstore.repository.ReviewRepository;
import com.ironhack.onlinebookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public List<ReviewDTO> findByBookId(Long bookId) {
        return reviewRepository.findByBookId(bookId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ReviewDTO save(ReviewDTO reviewDTO) {
        Review review = convertToEntity(reviewDTO);
        return convertToDTO(reviewRepository.save(review));
    }

    private ReviewDTO convertToDTO(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getBook().getId(),
                review.getUser().getId(),
                review.getRating(),
                review.getComment()
        );
    }

    private Review convertToEntity(ReviewDTO reviewDTO) {
        Book book = bookRepository.findById(reviewDTO.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setBook(book);
        review.setUser(user);
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());

        return review;
    }
}