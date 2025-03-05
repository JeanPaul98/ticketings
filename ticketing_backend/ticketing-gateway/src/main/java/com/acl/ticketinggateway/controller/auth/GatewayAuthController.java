package com.acl.ticketinggateway.controller.auth;

import com.acl.ticketinggateway.gatewayService.auth.GatewayAuthentificationService;
import com.acl.ticketinggateway.request.LoginRequestDto;
import com.acl.ticketinggateway.playload.ApiResponseModel;
import com.acl.ticketinggateway.request.RegisterRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
public class GatewayAuthController {
    final Logger logger = LoggerFactory.getLogger(GatewayAuthentificationService.class);
    private final GatewayAuthentificationService gatewayAuthentificationService;

    public GatewayAuthController(GatewayAuthentificationService gatewayAuthentificationService) {
        this.gatewayAuthentificationService = gatewayAuthentificationService;
    }

    /**
     *
     * @param loginRequestDto
     * @return
     */
    @PostMapping("login")
    @ApiOperation(value = "Connexion ", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: The User is not exist into database"),
            @ApiResponse(code = 401, message = "UNAUTORIZED: The User is not autorize to access  "),
            @ApiResponse(code = 200, message = "OK: The User is  connect  ")})
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        logger.info("Connexion utilisateur " + loginRequestDto.getUsername());
        return gatewayAuthentificationService.authUser(loginRequestDto);
    }
    @PostMapping("register")
    @ApiOperation(value = "Register ", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: The User is not exist into database"),
            @ApiResponse(code = 401, message = "UNAUTORIZED: The User is not autorize to access  "),
            @ApiResponse(code = 200, message = "OK: The User is  connect  ")})
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        logger.info("Connexion utilisateur " + request.getUsername());
        return gatewayAuthentificationService.createUser(request);
    }



}
