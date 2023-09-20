package com.fadesppagamentos.services;

import com.fadesppagamentos.domain.payment.Payment;
import com.fadesppagamentos.dtos.PaymentFormDto;
import com.fadesppagamentos.enumeration.DocumentEnum;
import com.fadesppagamentos.enumeration.PaymentMethodEnum;
import com.fadesppagamentos.repositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    PaymentFormDto payment;

    @BeforeEach
    public void setUp() {
        BigDecimal amount = new BigDecimal("300.00");
        payment = new PaymentFormDto("31231222", DocumentEnum.CPF, "02221261200", "1234124123", PaymentMethodEnum.CARTAO_CREDITO, amount);
    }

    @Test
    public void testCreatePayment() {
        Payment paymentToSave = new Payment();
        when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(paymentToSave);

        String result = paymentService.createPayment(payment);

        assertNotNull(result);
        assertEquals("Pagamento criado com sucesso!", result);

        verify(paymentRepository, times(1)).save(Mockito.any(Payment.class));
    }

    @Test
    public void testCreatePaymentFailed() {
        // Arrange
        when(paymentRepository.save(Mockito.any())).thenThrow(new RuntimeException("Simulated exception"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> paymentService.createPayment(payment));
    }
}

