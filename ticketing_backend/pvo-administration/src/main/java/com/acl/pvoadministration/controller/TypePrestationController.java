package com.acl.pvoadministration.controller;



import com.acl.pvoadministration.playload.ApiResponseModel;
import com.acl.pvoadministration.dto.TypePrestationDto;
import com.acl.pvoadministration.services.TypePrestationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/typePrestation")
@AllArgsConstructor
public class TypePrestationController {

    private final TypePrestationService typePrestationService;
    @CrossOrigin
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Creation type prestation", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
    ResponseEntity<?> typePrestation(@RequestBody @Valid TypePrestationDto typePrestationDto) {
            return typePrestationService.createType(typePrestationDto);
    }

    @CrossOrigin
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CAISSIER') ")
    @ApiOperation(value = "Lise des types de prestations", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: The types of prestations is not exist into database"),
            @ApiResponse(code = 200, message = "OK: List of TypePrestation  ")})
    ResponseEntity<?> getAllTypePrestations() {
        return typePrestationService.getAllTypePrestations();
    }
}
