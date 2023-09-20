package com.fadesppagamentos.domain.payment;

import com.fadesppagamentos.enumeration.DocumentEnum;
import com.fadesppagamentos.enumeration.PaymentMethodEnum;
import com.fadesppagamentos.enumeration.PaymentStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "payments")
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Enumerated(EnumType.STRING)
    private DocumentEnum documentType;

    private String document;

    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymantMethod;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatus;

    /**
     * Construtor para criar uma instância de Payment.
     *
     * @param payment O objeto Payment a ser copiado.
     */
    public Payment(Payment payment) {
        this.id = payment.getId();
        this.code = payment.getCode();
        this.documentType = payment.getDocumentType();
        this.document = payment.getDocument();
        this.cardNumber = payment.getCardNumber();
        this.paymantMethod = payment.getPaymantMethod();
        this.amount = payment.getAmount();
        this.paymentStatus = payment.getPaymentStatus();
    }
}
