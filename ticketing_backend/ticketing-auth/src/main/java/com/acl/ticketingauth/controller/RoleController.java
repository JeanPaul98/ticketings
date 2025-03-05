package com.acl.ticketingauth.controller;

import com.acl.ticketingauth.dto.AjoutRoleUserDto;
import com.acl.ticketingauth.dto.RoleDto;
import com.acl.ticketingauth.playload.JwtAuthenticationResponse;
import com.acl.ticketingauth.requests.LoginRequest;
import com.acl.ticketingauth.service.RoleService;
import com.acl.ticketingauth.validator.RetrieveValidationError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
@Api(tags = {"RoleController"}, value = "RoleController Rest Controller: Gérer les roles")
public class RoleController {
    private final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    private final RetrieveValidationError retrieveValidationError;

    public RoleController(RoleService roleService,RetrieveValidationError retrieveValidationError){
        this.roleService=roleService;
        this.retrieveValidationError=retrieveValidationError;
    }


    @CrossOrigin
    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Webservice pour afficher la liste des roles",
            response = JwtAuthenticationResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR: Données non conformes"),
            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
    public ResponseEntity<?>getAllRoles(){
        return roleService.getAllRolesActifs();
    }

    @CrossOrigin
    @PostMapping("create")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Webservice pour créer un role",
            response = JwtAuthenticationResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 406, message = "NOT_ACCEPTABLE: Données non conformes"),
            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
    public ResponseEntity<?>createRole(@Valid @RequestBody RoleDto roleDto, BindingResult result){
        logger.info("Role web " + roleDto);
        if (result.hasErrors()) {
            return retrieveValidationError.retrieveErrors(result);
        }
        return roleService.createRole(roleDto);
    }

    @CrossOrigin
    @PutMapping ("disable")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Webservice pour créer un role",
            response = JwtAuthenticationResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 406, message = "NOT_ACCEPTABLE: Données non conformes"),
            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
    public ResponseEntity<?>desactiverRole(@Valid @RequestBody AjoutRoleUserDto roleDto, BindingResult result){
        logger.info("Role web " + roleDto);
        if (result.hasErrors()) {
            return retrieveValidationError.retrieveErrors(result);
        }
        return roleService.desactiverRole(roleDto);
    }
}
