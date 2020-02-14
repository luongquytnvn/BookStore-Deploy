package com.codegym.controllers;

import com.codegym.models.Author;
import com.codegym.models.Book;
import com.codegym.models.BookPicture;
import com.codegym.models.order.Order;
import com.codegym.models.order.OrderItem;
import com.codegym.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/order-item")
public class OrderItemController {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @GetMapping("")
    public ResponseEntity<Iterable<OrderItem>> showListOrderItem() {
        Iterable<OrderItem> orderItems = orderItemRepository.findAll();
        return new ResponseEntity<Iterable<OrderItem>>(orderItems, HttpStatus.OK);
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<List<OrderItem>> findByOrderId(@PathVariable Long id) {
        List<OrderItem> orderItems = orderItemRepository.findByOrder_Id(id);
        return new ResponseEntity<List<OrderItem>>(orderItems, HttpStatus.OK);
    }

    @GetMapping("/cart/{idBook}/{idOrder}")
    public  ResponseEntity<OrderItem> findByBook_IdAndOrder_Id(@PathVariable Long idBook,@PathVariable Long idOrder) {
        OrderItem orderItem = orderItemRepository.findByBook_IdAndOrder_Id(idBook,idOrder);
        if (orderItem==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<OrderItem>(orderItem, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addNewOrderItem(@RequestBody OrderItem orderItem){
        try {
            orderItemRepository.save(orderItem);
            return new ResponseEntity<OrderItem>(orderItem, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id){
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        if (orderItem.isPresent()){
            System.out.println("find order item");
            return new ResponseEntity<OrderItem>(orderItem.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editOrderItem(@RequestBody OrderItem orderItem,@PathVariable Long id) {
        System.out.println("Updating order item " + id);
        Optional<OrderItem> currentOrderItem = orderItemRepository.findById(id);

        if (!currentOrderItem.isPresent()) {
            System.out.println("order item with id " + id + " not found");
            return new ResponseEntity<Optional<OrderItem>>(HttpStatus.NOT_FOUND);
        }
        System.out.println(orderItem.getQuantity());
        currentOrderItem.get().setQuantity(orderItem.getQuantity());
        currentOrderItem.get().setBook(orderItem.getBook());
        currentOrderItem.get().setOrder(orderItem.getOrder());
        orderItemRepository.save(currentOrderItem.get());
        return new ResponseEntity<Optional<OrderItem>>(currentOrderItem, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderItem(@PathVariable Long id) {
        Optional<OrderItem> currentOrderItem = orderItemRepository.findById(id);
        if (currentOrderItem.isPresent()) {
            orderItemRepository.delete(currentOrderItem.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
