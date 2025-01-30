package com.Park_Api.controller;

import com.Park_Api.controller.Requests.ClientRequest;
import com.Park_Api.controller.Responses.ClientResponse;
import com.Park_Api.entity.Client;
import com.Park_Api.entity.User;
import com.Park_Api.exceptions.errors.DataIntegrityViolationException;
import com.Park_Api.mapper.ClientMapper;
import com.Park_Api.service.ClienteService;
import com.Park_Api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/V1/client")
@Tag(name = "Client API", description = "Endpoints for managing clients")
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
    public ResponseEntity<ClientResponse> register(@Validated @RequestBody ClientRequest clientRequest,
                                                   @AuthenticationPrincipal User user) {

        if (user.getClient() != null) {
            throw new DataIntegrityViolationException("That user is already register an client!");
        }

        Client client = clientMapper.toClient(clientRequest);
        client.setUser(user);
        Client result = clienteService.save(client);

        return ResponseEntity.status(HttpStatus.CREATED).body(clientMapper.toResponse(result));
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClientResponse>> findAll() {

        List<Client> list = clienteService.findAll();
        return ResponseEntity.ok().body(clientMapper.toListResponse(list));
    }


    @GetMapping(value = "/details")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponse> getDetails(@AuthenticationPrincipal User user) {

        Client client = clienteService.findUserById(user.getId());

        return ResponseEntity.ok().body(clientMapper.toResponse(client));
    }
}
