package com.fadesppagamentos.repositories;

import com.fadesppagamentos.domain.payment.Payment;
import com.fadesppagamentos.enumeration.PaymentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByCode(String code);

    @Query("SELECT p FROM payments p " +
            "WHERE (:code IS NULL OR p.code = :code) " +
            "AND (:document IS NULL OR p.document = :document) " +
            "AND (:paymentStatus IS NULL OR p.paymentStatus = :paymentStatus)")
    List<Payment> findPaymentsByFilters(@Param("code") String code,
                                        @Param("document") String document,
                                        @Param("paymentStatus") PaymentStatusEnum paymentStatus);

}
