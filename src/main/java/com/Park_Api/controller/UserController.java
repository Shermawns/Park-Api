package com.Park_Api.controller;

import com.Park_Api.config.TokenService;
import com.Park_Api.controller.Requests.LoginRequest;
import com.Park_Api.controller.Requests.PasswordRequest;
import com.Park_Api.controller.Requests.UserRequest;
import com.Park_Api.controller.Responses.LoginResponse;
import com.Park_Api.controller.Responses.UserResponse;
import com.Park_Api.entity.User;
import com.Park_Api.exceptions.ErrorMessage;
import com.Park_Api.mapper.UserMapper;
import com.Park_Api.repository.UserRepository;
import com.Park_Api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/V1/user")
@Tag(name = "User API", description = "Endpoints for managing users, authentication, and authorization.")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserRepository userRepository, UserService userService, UserMapper userMapper, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }
@Operation(
        summary = "User Registration",
        description = "Endpoint for registering a new user. Requires a valid username and password.",
        responses = {
                @ApiResponse(responseCode = "201", description = "User successfully registered",
                        headers = @Header(name = HttpHeaders.LOCATION, description = "URL to access the created resource"),
                        content = @Content(mediaType = "application/json;charset=UTF-8",
                                schema = @Schema(implementation = UserResponse.class))),
                @ApiResponse(responseCode = "400", description = "User already exists",
                        content = @Content(mediaType = "application/json;charset=UTF-8",
                                schema = @Schema(implementation = ErrorMessage.class)))
        }
)
@PostMapping(value = "/register")
public ResponseEntity<UserResponse> create(@Validated @RequestBody UserRequest userRequest) {
    if (this.userRepository.findByUsername(userRequest.username()) != null)
        return ResponseEntity.badRequest().build();

    String encryptedPassword = new BCryptPasswordEncoder().encode(userRequest.password());
    User newUser = new User(userRequest.username(), encryptedPassword, userRequest.role(), userRequest.createdBy());
    User saveUser = this.userRepository.save(newUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserResponse(saveUser));
}

@Operation(
        summary = "User Login",
        description = "Endpoint for user authentication. Requires username and password.",
        responses = {
                @ApiResponse(responseCode = "200", description = "Successful authentication",
                        content = @Content(mediaType = "application/json;charset=UTF-8",
                                schema = @Schema(implementation = LoginResponse.class))),
                @ApiResponse(responseCode = "401", description = "Invalid credentials",
                        content = @Content(mediaType = "application/json;charset=UTF-8",
                                schema = @Schema(implementation = ErrorMessage.class)))
        }
)
@PostMapping("/login")
public ResponseEntity login(@RequestBody @Validated LoginRequest data) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
    var auth = this.authenticationManager.authenticate(usernamePassword);
    var token = tokenService.generateToken((User)auth.getPrincipal());
    return ResponseEntity.ok(new LoginResponse(token));
}

@Operation(
        summary = "Get All Users",
        description = "Retrieves a list of all registered users. Restricted to Role='ADMIN'.",
        security = @SecurityRequirement(name = "security"),
        responses = {
                @ApiResponse(responseCode = "200", description = "List of users retrieved successfully",
                        content = @Content(mediaType = "application/json;charset=UTF-8",
                                schema = @Schema(implementation = UserResponse.class))),
                @ApiResponse(responseCode = "403", description = "Access denied",
                        content = @Content(mediaType = "application/json;charset=UTF-8",
                                schema = @Schema(implementation = ErrorMessage.class)))
        }
)
@GetMapping
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<List<UserResponse>> findAll() {
    List<User> list = userService.findAll();
    return ResponseEntity.ok().body(userMapper.toListResponse(list));
}

@Operation(
        summary = "Find User by ID",
        description = "Retrieves user details by ID. Accessible to ADMIN and CLIENT (if requesting own data).",
        security = @SecurityRequirement(name = "security"),
        responses = {
                @ApiResponse(responseCode = "200", description = "User found successfully",
                        content = @Content(mediaType = "application/json;charset=UTF-8",
                                schema = @Schema(implementation = UserResponse.class))),
                @ApiResponse(responseCode = "403", description = "Access denied",
                        content = @Content(mediaType = "application/json;charset=UTF-8",
                                schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "User not found",
                        content = @Content(mediaType = "application/json;charset=UTF-8",
                                schema = @Schema(implementation = ErrorMessage.class)))
        }
)
@GetMapping(value = "/find/{id}")
@PreAuthorize("hasRole('ADMIN') OR ( hasRole('CLIENT') AND #id == principal.id )")
public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
    User user = userService.findById(id);
    return ResponseEntity.ok().body(userMapper.toUserResponse(user));
}

@Operation(
        summary = "Change User Password",
        description = "Allows users to change their password. Accessible to ADMIN (for self) and CLIENT (for self).",
        security = @SecurityRequirement(name = "security"),
        responses = {
                @ApiResponse(responseCode = "200", description = "Password changed successfully",
                        content = @Content(mediaType = "application/json;charset=UTF-8",
                                schema = @Schema(implementation = UserResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid password provided",
                        content = @Content(mediaType = "application/json;charset=UTF-8",
                                schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "403", description = "Access denied",
                        content = @Content(mediaType = "application/json;charset=UTF-8",
                                schema = @Schema(implementation = ErrorMessage.class)))
        }
)
@PatchMapping(value = "/changePassword/{id}")
@PreAuthorize("(hasRole('ADMIN') and #id == principal.id) or (hasRole('CLIENT') and #id == principal.id)")
public ResponseEntity<UserResponse> changePassword(@PathVariable Long id, @RequestBody @Valid PasswordRequest passwordRequest) {
    User user = userService.editPassword(id, passwordRequest.currentPassword(), passwordRequest.newPassword(), passwordRequest.confirmNewPassword());
    return ResponseEntity.ok().body(userMapper.toUserResponse(user));
    }
}
