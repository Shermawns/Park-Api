package com.Park_Api.controller;

import com.Park_Api.controller.Requests.ClientRequest;
import com.Park_Api.controller.Responses.ClientResponse;
import com.Park_Api.entity.Client;
import com.Park_Api.entity.User;
import com.Park_Api.mapper.ClientMapper;
import com.Park_Api.service.ClienteService;
import com.Park_Api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/V1/client")
public class ClientController {

    private final UserService userService;
    private final ClienteService clienteService;
    private final ClientMapper clientMapper;

    public ClientController(UserService userService, ClienteService clienteService, ClientMapper clientMapper) {
        this.userService = userService;
        this.clienteService = clienteService;
        this.clientMapper = clientMapper;
    }

    @PostMapping(value = "/register")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponse> create(@Validated @RequestBody ClientRequest clientRequest, @AuthenticationPrincipal User user){

        Client client = clientMapper.toClient(clientRequest);

        client.setUser(user);

        Client result = clienteService.save(client);

        return ResponseEntity.status(HttpStatus.CREATED).body(clientMapper.toResponse(result));
    }
}
