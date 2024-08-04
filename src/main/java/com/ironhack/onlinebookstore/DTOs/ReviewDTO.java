// ReviewDTO.java
package com.ironhack.onlinebookstore.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private Long bookId;
    private Long userId;
    private int rating;
    private String comment;
}