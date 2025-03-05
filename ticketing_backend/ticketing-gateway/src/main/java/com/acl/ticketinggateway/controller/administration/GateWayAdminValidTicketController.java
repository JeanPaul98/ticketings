package com.acl.ticketinggateway.controller.administration;

import com.acl.ticketinggateway.gatewayService.administration.GatewayAdministrationService;
import com.acl.ticketinggateway.gatewayService.auth.GatewayAuthentificationService;
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
@RequestMapping(value = "api/admin/ticket")
public class GateWayAdminValidTicketController {

    final Logger logger = LoggerFactory.getLogger(GatewayAuthentificationService.class);

    private final GatewayAdministrationService gatewayAdministrationService;

    public GateWayAdminValidTicketController(GatewayAdministrationService gatewayAdministrationService) {
        this.gatewayAdministrationService = gatewayAdministrationService;
    }



    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("code")
    @ApiOperation(value = "Ticket verification", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "ERROR"),
            @ApiResponse(code = 200, message = "OK")})
    ResponseEntity<?> getTransactionListe(@RequestBody TicketValidationRequest request) {
        return gatewayAdministrationService.getTicket(request);
    }
}
