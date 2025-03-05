package com.acl.ticketinggateway.controller.administration;

import com.acl.ticketinggateway.dto.PrestationDto;
import com.acl.ticketinggateway.dto.TypePrestationDto;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/typePrestation")
public class GateWayAdminTypePrestationController {

    final Logger logger = LoggerFactory.getLogger(GatewayAuthentificationService.class);
    private final GatewayAdministrationService gatewayAdministrationService;

    public GateWayAdminTypePrestationController(GatewayAdministrationService gatewayAdministrationService) {
        this.gatewayAdministrationService = gatewayAdministrationService;
    }

    /**
     * Connexion de l'utilisateur
     * @param
     * @return
     */

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("list")
    @ApiOperation(value = "List ", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: TypePrestation  is not exist into database"),
            @ApiResponse(code = 401, message = "UNAUTORIZED: TypePrestation is not autorize to access  "),
            @ApiResponse(code = 200, message = "OK: The User is  connect  ")})
    public ResponseEntity<?> getAll () {
        return gatewayAdministrationService.getAllTyPrestations();
    }

    /**
     * Connexion de l'utilisateur
     * @param
     * @return
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("")
    @ApiOperation(value = "Création ", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: The User is not exist into database"),
            @ApiResponse(code = 401, message = "UNAUTORIZED: The User is not autorize to access  "),
            @ApiResponse(code = 200, message = "OK: The User is  connect  ")})
    public ResponseEntity<?> createPrestation (@RequestBody TypePrestationDto request) {
        return gatewayAdministrationService.createTypePrestation(request);
    }

}
