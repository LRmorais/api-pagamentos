package com.fadesppagamentos.services;

import com.fadesppagamentos.domain.payment.Payment;
import com.fadesppagamentos.dtos.PaymentFormDto;
import com.fadesppagamentos.enumeration.PaymentStatusEnum;

import java.util.List;
import java.util.Optional;

public interface IPaymentService {

    Payment createPayment(PaymentFormDto paymentFormDto);

    Optional<Payment> getPaymentById(Long id);

    List<Payment> getAllPayments();

    List<Payment> filterPayments(String code, String document, PaymentStatusEnum paymentStatus);

    void updatePaymentStatus(Long id, PaymentStatusEnum newStatus);

    boolean deletePayment(Long id);
}
