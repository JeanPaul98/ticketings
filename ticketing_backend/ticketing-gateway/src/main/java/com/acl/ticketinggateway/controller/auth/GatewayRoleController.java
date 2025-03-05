package com.acl.ticketinggateway.controller.auth;

import com.acl.ticketinggateway.dto.AjoutRoleUserDto;
import com.acl.ticketinggateway.dto.RoleDto;
import com.acl.ticketinggateway.gatewayService.auth.GatewayAuthentificationService;
import com.acl.ticketinggateway.playload.ApiResponseModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
public class GatewayRoleController {
    final Logger logger = LoggerFactory.getLogger(GatewayRoleController.class);
    private final GatewayAuthentificationService gatewayAuthentificationService;

    public GatewayRoleController(GatewayAuthentificationService gatewayAuthentificationService) {
        this.gatewayAuthentificationService = gatewayAuthentificationService;
    }


    @GetMapping("")
    @ApiOperation(value = "Webservice pour afficher la liste des roles", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR: Données non conformes"),
            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
    public ResponseEntity<?> getAllRoles() {
        return gatewayAuthentificationService.getAllRoles();
    }

    @GetMapping("create")
    @ApiOperation(value = "Webservice pour créer un role", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 406, message = "NOT_ACCEPTABLE: Données non conformes"),
            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
    public ResponseEntity<?> createRole(@RequestBody @Valid RoleDto roleDto) {
        logger.info("Role web " + roleDto);
        return gatewayAuthentificationService.createRole(roleDto);
    }

    @GetMapping("disable")
    @ApiOperation(value = "Webservice pour créer un role", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 406, message = "NOT_ACCEPTABLE: Données non conformes"),
            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
    public ResponseEntity<?> desactiverRole(@RequestBody @Valid AjoutRoleUserDto roleDto) {
        logger.info("Role web " + roleDto);
        return gatewayAuthentificationService.desactiverRole(roleDto);
    }
}
