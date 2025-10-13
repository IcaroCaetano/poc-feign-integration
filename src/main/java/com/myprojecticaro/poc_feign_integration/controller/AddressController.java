package com.myprojecticaro.poc_feign_integration.controller;

import com.example.pocfeignintegration.dto.AddressResponse;
import com.example.pocfeignintegration.service.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code AddressController} exposes REST endpoints for retrieving
 * address information from the external {@code ViaCEP} API.
 *
 * <p>This controller delegates business logic to the {@link AddressService},
 * which handles integration with the external service and applies
 * resilience patterns such as retry and circuit breaker.</p>
 *
 * <p>Example request:</p>
 * <pre>
 * GET /address/01001000
 * </pre>
 *
 * <p>Example response:</p>
 * <pre>{@code
 * {
 *   "cep": "01001-000",
 *   "logradouro": "Praça da Sé",
 *   "bairro": "Sé",
 *   "localidade": "São Paulo",
 *   "uf": "SP"
 * }
 * }</pre>
 *
 * @author
 *     Icaro Caetano
 * @see AddressService
 * @see com.example.pocfeignintegration.client.ViaCepClient
 */
@RestController
public class AddressController {

    
    private final AddressService addressService;

     /**
     * Constructs an {@code AddressController} with the specified {@link AddressService}.
     *
     * @param addressService the service responsible for fetching address data
     *                       from the external API
     */
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

     /**
     * Retrieves address details for the given postal code (CEP) by delegating
     * the request to the {@link AddressService}.
     *
     * @param cep the Brazilian postal code (CEP) to be queried, e.g. {@code "01001000"}
     * @return an {@link AddressResponse} containing address information such as
     *         street, neighborhood, city, and state
     */
    @GetMapping("/address/{cep}")
    public AddressResponse getAddress(@PathVariable String cep) {
        return addressService.getAddress(cep);
    }
}

