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

    public Optional<Client> findById(Long id){

        Optional<Client> userId = clienteRepository.findById(id);

        if (userId.isEmpty()){
            throw new EntityNotFoundException("The user with ID "+ id + " doesn't exist.");
        }

        return userId;
    }

    public List<Client> findAll(){
        return clienteRepository.findAll();
    }


}
