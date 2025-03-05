package com.acl.ticketingpvocoris.controller;



import com.acl.ticketingpvocoris.playload.JwtAuthenticationResponse;
import com.acl.ticketingpvocoris.playload.LoginRequest;
import com.acl.ticketingpvocoris.services.AuthService;
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
    @PostMapping("login/tpe")
    @ApiOperation(value = "Webservice d'authentification User",
            response = JwtAuthenticationResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR: Données non conformes"),
            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
    public ResponseEntity<?> authenticateUserTpe(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("authentification Tpe loginRequest :"+loginRequest);
        return authService.connexionTpe(loginRequest);
    }


//    @CrossOrigin
//    @PostMapping("login/web")
//    @ApiOperation(value = "Webservice d'authentification web", response = JwtAuthenticationResponse.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 404, message = "NOT_FOUND: Data not found"),
//            @ApiResponse(code = 403, message = "FORBIDEN: Data not exist"),
//            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
//    public ResponseEntity<?> authenticateUserWeb(@Valid @RequestBody LoginRequest loginRequest) {
//        logger.info("authentification web loginRequest "+loginRequest);
//        return authService.connexionWeb(loginRequest);
//    }


}
