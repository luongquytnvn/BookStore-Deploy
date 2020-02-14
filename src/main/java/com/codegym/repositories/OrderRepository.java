package com.codegym.repositories;

import com.codegym.models.order.Order;
import com.codegym.models.order.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByStatusAndUser_Id(Status status, Long user_id);
    @Query(value = "select order_items_id from  orders join users on orders.user_id = users.id join orders_order_items on orders.id = orders_order_items.order_id where status = 'normal' and user_id = ?1", nativeQuery = true)
    List<Long> findIdOrderItemListByUserId(Long idUser);
    List<Order> findAllByUser_Id(Long user_id);
}
