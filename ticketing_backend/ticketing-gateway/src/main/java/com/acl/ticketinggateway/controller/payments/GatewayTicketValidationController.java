package com.acl.ticketinggateway.controller.payments;

import com.acl.ticketinggateway.gatewayService.auth.GatewayAuthentificationService;
import com.acl.ticketinggateway.gatewayService.paiement.GatewayPaiementEspeceService;
import com.acl.ticketinggateway.playload.ApiResponseModel;
import com.acl.ticketinggateway.request.TicketValidationRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket")
public class GatewayTicketValidationController {

    final Logger logger = LoggerFactory.getLogger(GatewayAuthentificationService.class);

    private final GatewayPaiementEspeceService especeService;

    public GatewayTicketValidationController(GatewayPaiementEspeceService especeService) {
        this.especeService = especeService;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("code")
    @ApiOperation(value = "Check ", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: The ticket is not exist into database"),
            @ApiResponse(code = 401, message = "UNAUTORIZED: The User is not autorize to access  "),
            @ApiResponse(code = 200, message = "OK: Verification success")})
    public ResponseEntity<?> ticketverification(@RequestBody TicketValidationRequest request) {
        return especeService.validateTicket(request);
    }

}
