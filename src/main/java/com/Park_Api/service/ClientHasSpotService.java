package com.Park_Api.service;

import com.Park_Api.entity.Client;
import com.Park_Api.entity.Garage;
import com.Park_Api.repository.GarageRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientHasSpotService {

    private final GarageRepository garageRepository;
    private final ClienteService clienteService;

    public ClientHasSpotService(GarageRepository garageRepository, ClienteService clienteService) {
        this.garageRepository = garageRepository;
        this.clienteService = clienteService;
    }

    public Garage save(Garage garage){

        return garageRepository.save(garage);

    }

    public Client findByCpf(String clientCpf) {
        return clienteService.findByCpf(clientCpf);
    }
}
