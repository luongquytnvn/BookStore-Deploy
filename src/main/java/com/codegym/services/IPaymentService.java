package com.codegym.services;

import com.codegym.models.Category;
import com.codegym.models.Payment;

import java.util.Optional;

public interface IPaymentService {
    Iterable<Payment> findAll();

    Payment findById(Long id);

    void save(Payment payment);

    void remove(Long id);
}
