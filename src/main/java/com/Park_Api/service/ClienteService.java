package com.Park_Api.service;

import com.Park_Api.entity.Client;
import com.Park_Api.exceptions.errors.EntityNotFoundException;
import com.Park_Api.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Client save(Client client){

        return clienteRepository.save(client);
    }

    public Client findById(Long id){

        Client userId = clienteRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Client %s not found in the system", id)));

        return userId;
    }

    public List<Client> findAll(){
        return clienteRepository.findAll();
    }


    public Client findUser(Long id) {
        return clienteRepository.findUserById(id);
    }
}
