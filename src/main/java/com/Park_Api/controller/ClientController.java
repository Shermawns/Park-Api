package com.Park_Api.controller;

import com.Park_Api.controller.Requests.ClientRequest;
import com.Park_Api.controller.Responses.ClientResponse;
import com.Park_Api.entity.Client;
import com.Park_Api.entity.User;
import com.Park_Api.exceptions.errors.DataIntegrityViolationException;
import com.Park_Api.mapper.ClientMapper;
import com.Park_Api.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import com.Park_Api.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@Tag(name = "Client API", description = "Endpoints for managing clients.")
public class ClientController {

    private final UserService userService;
    private final ClienteService clienteService;
    private final ClientMapper clientMapper;

    public ClientController(UserService userService, ClienteService clienteService, ClientMapper clientMapper) {
        this.userService = userService;
        this.clienteService = clienteService;
        this.clientMapper = clientMapper;
    }
@Operation(summary = "Register Client", description = "Registers a new client. " +
        "Only CLIENT role users can register themselves as clients.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client successfully registered",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ClientResponse.class))),
                    @ApiResponse(responseCode = "400", description = "User already has a registered client",
                            content = @Content(mediaType = "application/json;charset=UTF-8")),
})
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



    @Operation(summary = "List All Clients", description = "Retrieves a list of all registered clients. " +
            "Only accessible to ADMIN users.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of clients",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ClientResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied",
                            content = @Content(mediaType = "application/json;charset=UTF-8")),
            })

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClientResponse>> findAll() {
        List<Client> list = clienteService.findAll();
        return ResponseEntity.ok().body(clientMapper.toListResponse(list));
    }


    @Operation(summary = "Get Client Details", description = "Retrieves the authenticated client's details.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client details retrieved",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ClientResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied",
                            content = @Content(mediaType = "application/json;charset=UTF-8")),
            })

    @GetMapping(value = "/details")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponse> getDetails(@AuthenticationPrincipal User user) {

        Client client = clienteService.findUserById(user.getId());

        return ResponseEntity.ok().body(clientMapper.toResponse(client));
    }
}
