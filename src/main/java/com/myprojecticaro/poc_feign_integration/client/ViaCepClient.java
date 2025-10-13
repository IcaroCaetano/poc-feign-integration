package com.myprojecticaro.poc_feign_integration.client;

import com.example.pocfeignintegration.dto.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * {@code ViaCepClient} is a Feign client interface used to consume the
 * public {@code ViaCEP} API, which provides address information based on
 * a Brazilian postal code (CEP).
 *
 * <p>This client is configured with {@link com.example.pocfeignintegration.config.FeignConfig}
 * to customize Feign behavior, such as error decoding and logging.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * AddressResponse address = viaCepClient.getAddressByCep("01001000");
 * }</pre>
 *
 * @author
 *     Icaro Caetano
 * @see <a href="https://viacep.com.br/">ViaCEP API</a>
 * @see com.example.pocfeignintegration.config.FeignConfig
 */
@FeignClient(
    name = "viaCepClient",
    url = "https://viacep.com.br/ws",
    configuration = com.example.pocfeignintegration.config.FeignConfig.class
)
public interface ViaCepClient {

    /**
     * Retrieves address information from the ViaCEP API using the provided postal code (CEP).
     *
     * @param cep the Brazilian postal code (CEP) to look up, e.g. {@code "01001000"}.
     * @return an {@link AddressResponse} object containing the address details
     *         such as street, neighborhood, city, and state.
     *
     * @throws com.example.pocfeignintegration.exception.ApiException
     *         if the ViaCEP service is unavailable or returns an error response.
     */
    @GetMapping("/{cep}/json/")
    AddressResponse getAddressByCep(@PathVariable("cep") String cep);
}
