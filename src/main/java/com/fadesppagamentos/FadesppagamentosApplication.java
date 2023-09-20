package com.fadesppagamentos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Swagger API de Pagamentos", version = "1", description = "API de Pagamentos"))
public class FadesppagamentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(FadesppagamentosApplication.class, args);
	}

}
