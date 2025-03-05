package com.acl.ticketingauth.controller;


import com.acl.ticketingauth.playload.ApiResponseModel;
import com.acl.ticketingauth.playload.JwtAuthenticationResponse;
import com.acl.ticketingauth.requests.LoginRequest;
import com.acl.ticketingauth.dto.RegisterDto;
import com.acl.ticketingauth.service.AuthService;
import com.acl.ticketingauth.service.AuthUserService;
import com.acl.ticketingauth.validator.RetrieveValidationError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Api(tags = {"Authentificationdata"}, value = "AuthController Rest Controller: Gérer l'authentification")
public class AuthentificationController {

    private final Logger logger = LoggerFactory.getLogger(AuthentificationController.class);
    private final AuthService authService;

    private final RetrieveValidationError retrieveValidationError;

    private final AuthUserService authUserService;

    public AuthentificationController(AuthService authService,RetrieveValidationError retrieveValidationError,
                                      AuthUserService authUserService) {
        this.authService = authService;
        this.retrieveValidationError=retrieveValidationError;
        this.authUserService=authUserService;
    }

    @CrossOrigin
    @PostMapping("login/tpe")
    @ApiOperation(value = "Webservice d'authentification User",
            response = JwtAuthenticationResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR: Données non conformes"),
            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
    public ResponseEntity<?> authenticateUserTpe(@Valid @RequestBody LoginRequest loginRequest,BindingResult result) {
        logger.info("authentification Tpe loginRequest :" + loginRequest);
        if (result.hasErrors()) {
            return retrieveValidationError.retrieveErrors(result);
        }
        return authService.connexionTpe(loginRequest);
    }


    @CrossOrigin
    @PostMapping("login/web")
    @ApiOperation(value = "Webservice d'authentification web", response = JwtAuthenticationResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: Data not found"),
            @ApiResponse(code = 403, message = "FORBIDEN: Data not exist"),
            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
    public ResponseEntity<?> authenticateUserWeb(@Valid @RequestBody LoginRequest loginRequest,BindingResult result) {
        logger.info("authentification web loginRequest " + loginRequest);
        if (result.hasErrors()) {
            return retrieveValidationError.retrieveErrors(result);
        }
        return authService.connexionWeb(loginRequest);
    }


    @CrossOrigin
    @PostMapping("register")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Webservice register User in one or more roles", response = JwtAuthenticationResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: Data not found"),
            @ApiResponse(code = 403, message = "FORBIDEN: Data not exist"),
            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        logger.info("register web RegisterRequest " );
        return authUserService.createUser(registerDto);
    }

}
