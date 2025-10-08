package com.myprojecticaro.poc_feign_integration.controller;

import com.example.pocfeignintegration.dto.AddressResponse;
import com.example.pocfeignintegration.service.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/address/{cep}")
    public AddressResponse getAddress(@PathVariable String cep) {
        return addressService.getAddress(cep);
    }
}

