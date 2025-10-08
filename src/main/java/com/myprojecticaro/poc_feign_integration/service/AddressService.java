package com.myprojecticaro.poc_feign_integration.service;

import com.example.pocfeignintegration.client.ViaCepClient;
import com.example.pocfeignintegration.dto.AddressResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final ViaCepClient viaCepClient;

    public AddressService(ViaCepClient viaCepClient) {
        this.viaCepClient = viaCepClient;
    }

    @Retry(name = "viaCepRetry")
    @CircuitBreaker(name = "viaCepCircuitBreaker", fallbackMethod = "fallbackAddress")
    public AddressResponse getAddress(String cep) {
        return viaCepClient.getAddressByCep(cep);
    }

    public AddressResponse fallbackAddress(String cep, Throwable t) {
        AddressResponse response = new AddressResponse();
        response.setCep(cep);
        response.setLogradouro("Indisponível");
        response.setLocalidade("Serviço fora do ar");
        return response;
    }
}

