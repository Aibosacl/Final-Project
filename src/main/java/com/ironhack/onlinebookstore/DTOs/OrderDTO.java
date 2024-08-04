package com.ironhack.onlinebookstore.DTOs;

import com.ironhack.onlinebookstore.model.OrderStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDTO {
    private Long id;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Book IDs cannot be null")
    private List<Long> bookIds;

    @NotNull(message = "Order date cannot be null")
    @PastOrPresent(message = "Order date cannot be in the future")
    private Date orderDate;

    @NotNull(message = "Order status cannot be null")
    private OrderStatus status;

    @NotNull(message = "Total amount cannot be null")
    @Min(value = 0, message = "Total amount must be positive")
    private Double total;

    public OrderDTO(Long id, Long userId, List<Long> bookIds, Date orderDate, OrderStatus status, Double total) {
        this.id = id;
        this.userId = userId;
        this.bookIds = bookIds;
        this.orderDate = orderDate;
        this.status = status;
        this.total = total;
    }

    public <R> OrderDTO(Long id, R collect, Date orderDate, @NotNull(message
            = "Order status cannot be null") OrderStatus status,
                        @NotNull(message = "Total amount cannot be null") @Min(value = 0,
                                message = "Total amount must be positive") Double total) {
        this.id = id;
    }
}


