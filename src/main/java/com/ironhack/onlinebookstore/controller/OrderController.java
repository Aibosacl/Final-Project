package com.ironhack.onlinebookstore.controller;

import com.ironhack.onlinebookstore.DTOs.OrderDTO;
import com.ironhack.onlinebookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/userid/{userid}")
    public ResponseEntity<List<OrderDTO>> listOrders(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromPrincipal(userDetails);
        List<OrderDTO> orders = orderService.findAllByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OrderDTO> viewOrder(@PathVariable Long id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/userdetails/{userdetails}")
    public ResponseEntity<OrderDTO> addOrder(@Validated @Lazy @RequestBody OrderDTO orderDTO,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromPrincipal(userDetails);
        orderDTO.setUserId(userId);
        OrderDTO savedOrder = orderService.save(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @DeleteMapping("/deleteorder/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Long getUserIdFromPrincipal(UserDetails userDetails) {
        // Assuming that the username is the user ID
        return Long.valueOf(userDetails.getUsername());
    }
}
