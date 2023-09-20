package com.fadesppagamentos.services;

import com.fadesppagamentos.domain.payment.Payment;
import com.fadesppagamentos.dtos.PaymentDto;
import com.fadesppagamentos.dtos.PaymentFormDto;
import com.fadesppagamentos.enumeration.PaymentMethodEnum;
import com.fadesppagamentos.enumeration.PaymentStatusEnum;
import com.fadesppagamentos.repositories.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService implements IPaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public String createPayment(PaymentFormDto paymentFormDto) {
        String code = paymentFormDto.code();
        Optional<Payment> existingPayment = paymentRepository.findByCode(code);

        if (existingPayment.isPresent()) {
            throw new DataIntegrityViolationException("Pagamento já processado ou em processamento");
        }

        verifyCardNumber(paymentFormDto);

        Payment payment = new Payment();
        payment.setCode(code);
        payment.setDocumentType(paymentFormDto.documentType());
        payment.setDocument(paymentFormDto.document());
        payment.setCardNumber(paymentFormDto.cardNumber());
        payment.setPaymentMethod(paymentFormDto.paymentMethod());
        payment.setAmount(paymentFormDto.amount());
        payment.setPaymentStatus(PaymentStatusEnum.PENDENTE_PROCESSAMENTO);

        paymentRepository.save(payment);

        return "Pagamento criado com sucesso!";
    }

    public PaymentDto getPaymentById(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        return payment.map(p -> modelMapper.map(p, PaymentDto.class)).orElse(null);
    }
    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        if (payments.isEmpty()) {
            throw new EntityNotFoundException("Nenhum pagamento encontrado");
        }
        return payments.stream()
                .map(payment -> modelMapper.map(payment, PaymentDto.class))
                .collect(Collectors.toList());
    }

    public List<PaymentDto> filterPayments(String code, String document, PaymentStatusEnum paymentStatus) {
        List<Payment> payments = paymentRepository.findPaymentsByFilters(code, document, paymentStatus);
        if (payments.isEmpty()) {
            throw new EntityNotFoundException("Nenhum pagamento encontrado");
        }
        List<PaymentDto> paymentDtos = payments.stream()
                .map(payment -> modelMapper.map(payment, PaymentDto.class))
                .collect(Collectors.toList());

        return paymentDtos;
    }

    public String updatePaymentStatus(Long id, PaymentStatusEnum newStatus) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            if (payment.getPaymentStatus() == PaymentStatusEnum.PENDENTE_PROCESSAMENTO) {
                payment.setPaymentStatus(newStatus);
                paymentRepository.save(payment);
                return "Status do pagamento atualizado com sucesso!";
            } else if (payment.getPaymentStatus() == PaymentStatusEnum.PROCESSADO_FALHA && newStatus == PaymentStatusEnum.PENDENTE_PROCESSAMENTO) {
                payment.setPaymentStatus(newStatus);
                paymentRepository.save(payment);
                return "Status do pagamento atualizado com sucesso!";
            } else {
                throw new DataIntegrityViolationException("Pagamento já processado com sucesso");
            }
        } else {
            throw new EntityNotFoundException("Pagamento não encontrado");
        }
    }


    public String deletePayment(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isEmpty()) {
            throw new EntityNotFoundException("Pagamento não encontrado");
        }

        Payment payment = paymentOptional.get();
        if (payment.getPaymentStatus() != PaymentStatusEnum.PENDENTE_PROCESSAMENTO) {
            throw new DataIntegrityViolationException("Pagamento já foi processado");
        }

        paymentRepository.delete(payment);

        return "Pagamento deletado com sucesso!";
    }

    public void verifyCardNumber(PaymentFormDto paymentFormDto) {
        PaymentMethodEnum paymentMethod = paymentFormDto.getPaymentMethod();
        String cardNumber = paymentFormDto.getCardNumber();

        if ((PaymentMethodEnum.CARTAO_CREDITO.equals(paymentMethod) || PaymentMethodEnum.CARTAO_DEBITO.equals(paymentMethod))
                && cardNumber == null) {
            throw new DataIntegrityViolationException("O número do cartão é obrigatório para pagamentos com cartão de crédito ou débito.");
        }

        if (!(PaymentMethodEnum.CARTAO_CREDITO.equals(paymentMethod) || PaymentMethodEnum.CARTAO_DEBITO.equals(paymentMethod))
                && cardNumber != null) {
            throw new DataIntegrityViolationException("O número do cartão deve ser utilizado para pagamentos com cartão de crédito ou débito.");
        }
    }
}
