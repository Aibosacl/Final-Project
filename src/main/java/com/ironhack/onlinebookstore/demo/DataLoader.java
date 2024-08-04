package com.ironhack.onlinebookstore.demo;

import com.ironhack.onlinebookstore.model.Book;
import com.ironhack.onlinebookstore.model.Order;
import com.ironhack.onlinebookstore.model.OrderStatus;
import com.ironhack.onlinebookstore.model.User;
import com.ironhack.onlinebookstore.repository.BookRepository;
import com.ironhack.onlinebookstore.repository.OrderRepository;
import com.ironhack.onlinebookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    public void loadData() {
        // Users
        addUserIfNotExists("NijaBlood", "A****_07", "ROLE_USER", "dnijablood@t-online.de");
        addUserIfNotExists("GermoGuy", "B****_17", "ROLE_USER", "germoguy@gmail.com");

        // Books
        Book book1 = bookRepository.save(new Book(null, "Book Title 1", "Author 1", "Genre 1", 19.99));
        Book book2 = bookRepository.save(new Book(null, "Book Title 2", "Author 2", "Genre 2", 29.99));
        Book book3 = bookRepository.save(new Book(null, "Book Title 3", "Author 3", "Genre 3", 39.99));
        Book book4 = bookRepository.save(new Book(null, "Book Title 4", "Author 4", "Genre 4", 49.99));
        Book book5 = bookRepository.save(new Book(null, "Book Title 5", "Author 5", "Genre 5", 59.99));

        // Orders
        Optional<User> nijaBlood = userRepository.findByUsername("NijaBlood");
        nijaBlood.ifPresent(user -> createOrderIfNotExists(1L, user, Arrays.asList(book1, book2), 19.99 + 29.99,
                OrderStatus.PENDING));
        Optional<User> germoGuy = userRepository.findByUsername("GermoGuy");
        germoGuy.ifPresent(user -> createOrderIfNotExists(2L, user, Arrays.asList(book2, book3), 29.99 + 39.99,
                OrderStatus.COMPLETED));
        Optional<User> donHenry = userRepository.findByUsername("DonHenry");
        donHenry.ifPresent(user -> createOrderIfNotExists(3L, user, Arrays.asList(book5, book4), 49.99 + 59.99,
                OrderStatus.CANCELLED));
    }

    private void addUserIfNotExists(String username, String password, String role, String email) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            userRepository.save(new User(null, username, passwordEncoder.encode(password), role, email));
        }
    }

    private void createOrderIfNotExists(Long orderId, User user, List<Book> books, Double total, OrderStatus status) {
        if (orderRepository.findById(orderId).isEmpty()) {
            Order order = new Order();
            order.setUser(user);
            order.setBooks(books);
            order.setTotal(total);
            order.setStatus(status);
            orderRepository.save(order);
        }
    }
}