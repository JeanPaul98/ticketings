package com.acl.ticketinggateway.controller.administration;

import com.acl.ticketinggateway.gatewayService.administration.GatewayAdministrationService;
import com.acl.ticketinggateway.gatewayService.auth.GatewayAuthentificationService;
import com.acl.ticketinggateway.playload.ApiResponseModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping(value = "api/reporting")
public class GateWayAdminReport {
    final Logger logger = LoggerFactory.getLogger(GatewayAuthentificationService.class);

    private final GatewayAdministrationService gatewayAdministrationService;

    public GateWayAdminReport(GatewayAdministrationService gatewayAdministrationService) {
        this.gatewayAdministrationService = gatewayAdministrationService;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("coris")
    @ApiOperation(value = "List des transactions ", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK: liste des transactions ")})
    ResponseEntity<byte[]> reporting(
            @RequestParam String dateDebut, @RequestParam String dateFin) {
        return gatewayAdministrationService.reportingCoris(dateDebut,dateFin);
    }

}
