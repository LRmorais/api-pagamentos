package com.fadesppagamentos.dtos;

import com.fadesppagamentos.enumeration.DocumentEnum;
import com.fadesppagamentos.enumeration.PaymentMethodEnum;
import java.math.BigDecimal;

public record PaymentFormDto(String code, DocumentEnum documentType, String document, String cardNumber, PaymentMethodEnum paymentMethod, BigDecimal amount) {
}
