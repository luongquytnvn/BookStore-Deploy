package com.codegym.models.order;

import com.codegym.models.Payment;
import com.codegym.models.user.User;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;

    @OneToMany(targetEntity = OrderItem.class, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    private String phone;

    private String shippingAddress;

    private Double total;

    @ManyToOne
    private Payment payment;

    public Order() {
    }

    public Order(Long id, User user, Date date, Status status, List<OrderItem> orderItems, String phone, String shippingAddress, Double total) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.status = status;
        this.orderItems = orderItems;
        this.phone = phone;
        this.shippingAddress = shippingAddress;
        this.total = total;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Order(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItem() {
        return orderItems;
    }

    public void setOrderItem(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
