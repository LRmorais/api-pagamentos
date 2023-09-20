package com.fadesppagamentos.services;

import com.fadesppagamentos.dtos.PaymentDto;
import com.fadesppagamentos.dtos.PaymentFormDto;
import com.fadesppagamentos.enumeration.PaymentStatusEnum;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface IPaymentService {

    String createPayment(PaymentFormDto paymentFormDto);

    PaymentDto getPaymentById(Long id) throws EntityNotFoundException;

    List<PaymentDto> getAllPayments();

    List<PaymentDto> filterPayments(String code, String document, PaymentStatusEnum paymentStatus);

    String updatePaymentStatus(Long id, PaymentStatusEnum newStatus);

    String deletePayment(Long id);
}
