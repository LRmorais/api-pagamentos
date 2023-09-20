package com.fadesppagamentos.services;

import com.fadesppagamentos.domain.payment.Payment;
import com.fadesppagamentos.dtos.PaymentFormDto;
import com.fadesppagamentos.enumeration.PaymentStatusEnum;
import com.fadesppagamentos.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

        public Payment createPayment(PaymentFormDto paymentFormDto) {
            Payment payment = new Payment();
            payment.setCode(paymentFormDto.code());
            payment.setDocumentType(paymentFormDto.documentType());
            payment.setDocument(paymentFormDto.document());
            payment.setCardNumber(paymentFormDto.cardNumber());
            payment.setPaymantMethod(paymentFormDto.paymentMethod());
            payment.setAmount(paymentFormDto.amount());
            payment.setPaymentStatus(PaymentStatusEnum.PENDENTE_PROCESSAMENTO);

            return paymentRepository.save(payment);
        }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> filterPayments(String code, String document, PaymentStatusEnum paymentStatus) {
        return paymentRepository.findPaymentsByFilters(code, document, paymentStatus);
    }

    public void updatePaymentStatus(Long id, PaymentStatusEnum newStatus) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            if (payment.getPaymentStatus() == PaymentStatusEnum.PENDENTE_PROCESSAMENTO) {
                payment.setPaymentStatus(newStatus);
                paymentRepository.save(payment);
            }
        }
    }

    public boolean deletePayment(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            if (payment.getPaymentStatus() == PaymentStatusEnum.PENDENTE_PROCESSAMENTO) {
                paymentRepository.delete(payment);
                return true;
            }
        }
        return false;
    }

}
