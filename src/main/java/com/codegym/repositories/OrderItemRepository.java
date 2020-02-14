package com.codegym.repositories;

import com.codegym.models.Book;
import com.codegym.models.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    OrderItem findByBook_IdAndOrder_Id(Long book_id, Long order_id);
    List<OrderItem> findByOrder_Id(Long order_id);
}
