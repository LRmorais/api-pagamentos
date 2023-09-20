package com.fadesppagamentos.dtos;

import com.fadesppagamentos.enumeration.DocumentEnum;
import com.fadesppagamentos.enumeration.PaymentMethodEnum;
import com.fadesppagamentos.enumeration.PaymentStatusEnum;

public record PaymentDto(String Code, DocumentEnum documentType, String Document, PaymentMethodEnum paymantMethod, String amount, PaymentStatusEnum paymentStatus) {
}
