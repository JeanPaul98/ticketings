package com.acl.pvoadministration.controller;

import com.acl.pvoadministration.dto.PrestationDto;
import com.acl.pvoadministration.playload.ApiResponseModel;
import com.acl.pvoadministration.services.PrestationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@RestController
@RequestMapping("/api/prestation")
public class PrestationController {
    private final PrestationService prestationService;

    public PrestationController(PrestationService prestationService) {
        this.prestationService = prestationService;
    }

    @CrossOrigin
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Creation prestation", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request: The name field is required"),
            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
    ResponseEntity<?> createPrestation(@RequestBody @Valid PrestationDto prestationDto) {
        return prestationService.save(prestationDto);
    }

    @CrossOrigin
    @GetMapping("detail/{designation}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CAISSIER') ")
    @ApiOperation(value = "Prestation en fonction de la designation", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
    ResponseEntity<?> findPrestationByDesignation(@RequestParam String designation) {
        return prestationService.getPrestationDetail(designation);
    }

    @CrossOrigin
    @GetMapping("list")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CAISSIER') ")
    @ApiOperation(value = "Lise des Prestations", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: The prestation is not exist into database"),
            @ApiResponse(code = 200, message = "OK: List of prestation  ")})
    ResponseEntity<?> getAllPrestations() {
        return prestationService.getAllPrestations();
    }
}
