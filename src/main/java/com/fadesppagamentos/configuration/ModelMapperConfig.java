package com.fadesppagamentos.configuration;

import com.fadesppagamentos.domain.payment.Payment;
import com.fadesppagamentos.dtos.PaymentDto;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        modelMapper.addConverter(new AbstractConverter<Payment, PaymentDto>() {
            @Override
            protected PaymentDto convert(Payment source) {
                return new PaymentDto(
                        source.getId(),
                        source.getCode(),
                        source.getDocumentType(),
                        source.getDocument(),
                        source.getPaymentMethod(),
                        source.getAmount(),
                        source.getPaymentStatus()
                );
            }
        });

        return modelMapper;
    }
}


