package com.fadesppagamentos.dtos;

import com.fadesppagamentos.enumeration.DocumentEnum;
import com.fadesppagamentos.enumeration.PaymentMethodEnum;
import com.fadesppagamentos.enumeration.PaymentStatusEnum;

import java.math.BigDecimal;

public record PaymentDto(Long id, String code, DocumentEnum documentType, String document, PaymentMethodEnum paymentMethod, BigDecimal amount, PaymentStatusEnum paymentStatus) {
}

