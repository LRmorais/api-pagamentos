package com.fadesppagamentos.controllers;

import com.fadesppagamentos.dtos.PaymentDto;
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

@RestController
@RequestMapping(value = "/api/payments", produces = "application/json")
@Tag(name = "Payment", description = "Pagamentos")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "Cria um pagamento", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pagamento já processado ou em processamento"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<String> createPayment(@RequestBody PaymentFormDto paymentFormDto) {
        String message = paymentService.createPayment(paymentFormDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @Operation(summary = "Busca um pagamento por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os dados de pagamento"),
            @ApiResponse(responseCode = "400", description = "Pagamento já processado"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long id) {
        PaymentDto payment = paymentService.getPaymentById(id);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @Operation(summary = "Busca todos os pagamentos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "retorna pagamentos encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum pagamento encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/all")
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        List<PaymentDto> paymentDtos = paymentService.getAllPayments();
        return new ResponseEntity<>(paymentDtos, HttpStatus.OK);
    }

    @Operation(summary = "Busca todos os pagamentos de um usuário", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamentos encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum pagamento encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/filter")
    public ResponseEntity<List<PaymentDto>> filterPayments(@RequestParam(required = false) String code,
                                                        @RequestParam(required = false) String document,
                                                        @RequestParam(required = false) PaymentStatusEnum paymentStatus) {
        List<PaymentDto> filteredPayments = paymentService.filterPayments(code, document, paymentStatus);
        return new ResponseEntity<>(filteredPayments, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza o status de um pagamento", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do pagamento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam PaymentStatusEnum newStatus
    ) {
        String message = paymentService.updatePaymentStatus(id, newStatus);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @Operation(summary = "Deleta um pagamento", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pagamento já processado"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        String message = paymentService.deletePayment(id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
