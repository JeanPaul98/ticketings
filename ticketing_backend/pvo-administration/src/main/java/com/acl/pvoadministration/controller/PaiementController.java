package com.acl.pvoadministration.controller;

import com.acl.pvoadministration.playload.ApiResponseModel;
import com.acl.pvoadministration.playload.JwtAuthenticationResponse;
import com.acl.pvoadministration.services.PaiementService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@AllArgsConstructor
@RequestMapping(value = "api/payments")
public class PaiementController {

    private final PaiementService paiementService;


    @CrossOrigin
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "liste des transactions", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK: liste des transactions ")})
    ResponseEntity<?> getTransaction() {
        return paiementService.listePaiement();
    }


    @CrossOrigin
    @GetMapping("/list/day")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "liste des transactions", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK: liste des transactions ")})
    ResponseEntity<?> getTransactionDay() {
        return paiementService.findAllTransactionDay();
    }

    @CrossOrigin
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN') ")
    @ApiOperation(value = "liste des transactions", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK: liste des transactions ")})
    ResponseEntity<?> searchTransaction(@RequestParam String searchValue){
        return paiementService.search(searchValue);
    }


    @CrossOrigin
    @GetMapping("date/search")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "liste des transactions", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK: liste des transactions ")})
    public ResponseEntity<?> filter(
            @RequestParam("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") String dateDebutStr,
            @RequestParam("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFinStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDebut = dateFormat.parse(dateDebutStr);
        Date dateFin = dateFormat.parse(dateFinStr);
        return paiementService.searchByDate(dateDebut,dateFin);
    }


    @CrossOrigin
    @GetMapping("date/filter")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "liste des transactions", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK: liste des transactions ")})
    public ResponseEntity<?> searchAndDay(
            @RequestParam("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") String dateDebutStr,
            @RequestParam("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFinStr,
            @RequestParam String searchValue) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDebut = dateFormat.parse(dateDebutStr);
        Date dateFin = dateFormat.parse(dateFinStr);
        return paiementService.searchAndDate(dateDebut,dateFin,searchValue);
    }

}
