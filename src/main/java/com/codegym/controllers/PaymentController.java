package com.codegym.controllers;

import com.codegym.models.Payment;
import com.codegym.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private IPaymentService paymentService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        List<Payment> payments = (List<Payment>) paymentService.findAll();
        if (payments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Payment>>(payments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Payment payment = paymentService.findById(id);
        if (payment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Payment>(payment, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
        if (payment == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        paymentService.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        Payment currentPayment = paymentService.findById(id);
        if (currentPayment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentPayment.setName(payment.getName());
        paymentService.save(currentPayment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
        Payment payment = paymentService.findById(id);
        if (payment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        paymentService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
