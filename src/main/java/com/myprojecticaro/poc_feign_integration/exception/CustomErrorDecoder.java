package com.myprojecticaro.poc_feign_integration.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomErrorDecoder implements ErrorDecoder {

    private static final Logger log = LoggerFactory.getLogger(CustomErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("Erro Feign: {} - status: {}", methodKey, response.status());
        return new ApiException("Erro ao consultar ViaCEP. Código: " + response.status());
    }
}
