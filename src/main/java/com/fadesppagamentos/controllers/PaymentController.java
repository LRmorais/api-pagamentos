package com.fadesppagamentos.controllers;

import com.fadesppagamentos.domain.payment.Payment;
import com.fadesppagamentos.dtos.PaymentFormDto;
import com.fadesppagamentos.enumeration.PaymentStatusEnum;
import com.fadesppagamentos.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/payments", produces = "application/json")
@Tag(name = "Payment", description = "Pagamentos")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "Cria um pagamento", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentFormDto paymentFormDto) {
        Payment createdPayment = paymentService.createPayment(paymentFormDto);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca um pagamento por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Busca todos os pagamentos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamentos encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @Operation(summary = "Busca todos os pagamentos de um usuário", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamentos encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/filter")
    public ResponseEntity<List<Payment>> filterPayments(@RequestParam(required = false) String code,
                                                        @RequestParam(required = false) String document,
                                                        @RequestParam(required = false) PaymentStatusEnum paymentStatus) {
        List<Payment> filteredPayments = paymentService.filterPayments(code, document, paymentStatus);
        return new ResponseEntity<>(filteredPayments, HttpStatus.OK);
    }

    @Operation(summary = "Deleta um pagamento", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pagamento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        boolean deleted = paymentService.deletePayment(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
