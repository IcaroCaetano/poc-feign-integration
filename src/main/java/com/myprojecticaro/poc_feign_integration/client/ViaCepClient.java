package com.myprojecticaro.poc_feign_integration.client;

import com.example.pocfeignintegration.dto.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "viaCepClient",
    url = "https://viacep.com.br/ws",
    configuration = com.example.pocfeignintegration.config.FeignConfig.class
)
public interface ViaCepClient {

    @GetMapping("/{cep}/json/")
    AddressResponse getAddressByCep(@PathVariable("cep") String cep);
}
