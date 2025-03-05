package com.acl.ticketinggateway.controller.administration;

import com.acl.ticketinggateway.gatewayService.administration.GatewayAdministrationService;
import com.acl.ticketinggateway.gatewayService.auth.GatewayAuthentificationService;
import com.acl.ticketinggateway.playload.ApiResponseModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "api/payments")
public class GateWayAdminPaiementController {

    final Logger logger = LoggerFactory.getLogger(GatewayAuthentificationService.class);

    private final GatewayAdministrationService gatewayAdministrationService;

    public GateWayAdminPaiementController(GatewayAdministrationService gatewayAdministrationService) {
        this.gatewayAdministrationService = gatewayAdministrationService;
    }



    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/list/day")
    @ApiOperation(value = "List des transactions de la journée ", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK: liste des transactions ")})
    ResponseEntity<?> getTransactionDay() {
        return gatewayAdministrationService.getTransactionDay();
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/list")
    @ApiOperation(value = "List des transactions ", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK: liste des transactions ")})
    ResponseEntity<?> getTransaction() {
        return gatewayAdministrationService.getTransaction();
    }



    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/search")
    @ApiOperation(value = "List des transactions ", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK: liste des transactions ")})
    ResponseEntity<?> searchTransaction(@RequestParam String searchValue) {
        return gatewayAdministrationService.searchTransaction(searchValue);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("date/search")
    @ApiOperation(value = "List des transactions ", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK: liste des transactions ")})
    ResponseEntity<?> searchTransaction(
            @RequestParam String dateDebut, @RequestParam String dateFin) throws ParseException {
        return gatewayAdministrationService.filterTransaction(dateDebut,dateFin);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("date/filter")
    @ApiOperation(value = "List des transactions ", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 200, message = "OK: liste des transactions ")})
    ResponseEntity<?> searchAndDay(
            @RequestParam String dateDebut, @RequestParam String dateFin,@RequestParam String searchValue) throws ParseException {
        return gatewayAdministrationService.filterDayAndSearch(dateDebut,dateFin,searchValue);
    }
}
