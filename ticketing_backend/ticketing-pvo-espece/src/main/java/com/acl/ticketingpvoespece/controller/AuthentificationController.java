package com.acl.ticketingpvoespece.controller;


import com.acl.ticketingpvoespece.playload.JwtAuthenticationResponse;
import com.acl.ticketingpvoespece.playload.LoginRequest;
import com.acl.ticketingpvoespece.security.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Api(tags = {"Authentificationdata"}, value = "AuthController Rest Controller: Gérer l'authentification")
public class AuthentificationController {

    private final Logger logger= LoggerFactory.getLogger(AuthentificationController.class);
    private final AuthService authService;

    public AuthentificationController(AuthService authService) {
        this.authService = authService;
    }

    @CrossOrigin
    @PostMapping("login")
   // @PreAuthorize("hasRole('CAISSIER')")
    @ApiOperation(value = "Webservice d'authentification", response = JwtAuthenticationResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR: Données non conformes"),
            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("authentification loginRequest"+loginRequest);
        return authService.connexionTpe(loginRequest);
    }


}
