package com.ironhack.onlinebookstore.service;

import com.ironhack.onlinebookstore.DTOs.OrderDTO;
import com.ironhack.onlinebookstore.model.Book;
import com.ironhack.onlinebookstore.model.Order;
import com.ironhack.onlinebookstore.model.User;
import com.ironhack.onlinebookstore.repository.BookRepository;
import com.ironhack.onlinebookstore.repository.OrderRepository;
import com.ironhack.onlinebookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    public List<OrderDTO> findAllByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<OrderDTO> findById(Long id) {
        return orderRepository.findById(id).map(this::convertToDTO);
    }

    public OrderDTO save(OrderDTO orderDTO) {
        Order order = convertToEntity(orderDTO);
        return convertToDTO(orderRepository.save(order));
    }

    @Transactional
    public void delete(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Order with id " + id + " does not exist.");
        }
    }

    // Converts an Order entity to an OrderDTO
    private OrderDTO convertToDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getBooks().stream().map(Book::getId).collect(Collectors.toList()),
                order.getOrderDate(),
                order.getStatus(),
                order.getTotal()
        );
    }

    // Converts an OrderDTO to an Order entity
    private Order convertToEntity(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException(
                "User not found"));
        List<Book> books = bookRepository.findAllById(orderDTO.getBookIds());

        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setBooks(books);
        order.setTotal(orderDTO.getTotal());
        order.setStatus(orderDTO.getStatus());

        return order;
    }
}
