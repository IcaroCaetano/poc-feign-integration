package com.myprojecticaro.poc_feign_integration.service;

import com.example.pocfeignintegration.client.ViaCepClient;
import com.example.pocfeignintegration.dto.AddressResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

/**
 * {@code AddressService} is responsible for retrieving address information
 * from the external {@code ViaCEP} API through the {@link ViaCepClient}.
 *
 * <p>This service uses <b>Resilience4j</b> annotations to ensure fault tolerance:
 * <ul>
 *     <li>{@link Retry} - Automatically retries the request in case of transient failures.</li>
 *     <li>{@link CircuitBreaker} - Opens the circuit and prevents repeated calls to a failing service.</li>
 * </ul>
 * </p>
 *
 * <p>When the {@code ViaCEP} service is unavailable or fails repeatedly,
 * a fallback method ({@link #fallbackAddress(String, Throwable)}) is executed
 * to provide a default response with minimal data, ensuring that the application
 * remains stable and responsive.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * AddressResponse address = addressService.getAddress("01001000");
 * }</pre>
 *
 * @author
 *     Icaro Caetano
 * @see ViaCepClient
 * @see io.github.resilience4j.retry.annotation.Retry
 * @see io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
 */
@Service
public class AddressService {

    private final ViaCepClient viaCepClient;

    /**
     * Constructs an {@code AddressService} with the specified {@link ViaCepClient}.
     *
     * @param viaCepClient the Feign client used to access the ViaCEP API
     */
    public AddressService(ViaCepClient viaCepClient) {
        this.viaCepClient = viaCepClient;
    }

    /**
     * Retrieves an address by postal code (CEP) from the ViaCEP API.
     * <p>
     * This method is protected by a {@link Retry} policy and a {@link CircuitBreaker}.
     * If multiple retries fail or the circuit is open, the fallback method
     * {@link #fallbackAddress(String, Throwable)} will be executed.
     * </p>
     *
     * @param cep the Brazilian postal code (CEP) to be queried
     * @return an {@link AddressResponse} containing address details
     */
    @Retry(name = "viaCepRetry")
    @CircuitBreaker(name = "viaCepCircuitBreaker", fallbackMethod = "fallbackAddress")
    public AddressResponse getAddress(String cep) {
        return viaCepClient.getAddressByCep(cep);
    }

        /**
     * Fallback method executed when the ViaCEP service is unavailable
     * or when the circuit breaker is open.
     *
     * @param cep the postal code that was being queried
     * @param t the cause of the failure
     * @return a default {@link AddressResponse} with placeholder values
     *         indicating that the service is temporarily unavailable
     */
    public AddressResponse fallbackAddress(String cep, Throwable t) {
        AddressResponse response = new AddressResponse();
        response.setCep(cep);
        response.setLogradouro("Indisponível");
        response.setLocalidade("Serviço fora do ar");
        return response;
    }
}

