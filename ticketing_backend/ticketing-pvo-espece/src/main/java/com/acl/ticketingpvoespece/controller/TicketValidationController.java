package com.acl.ticketingpvoespece.controller;

import com.acl.ticketingpvoespece.playload.ApiResponseModel;
import com.acl.ticketingpvoespece.dto.TicketValidationDto;
import com.acl.ticketingpvoespece.services.TicketValidationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket")
public class TicketValidationController {

    private final TicketValidationService ticketValidationService;

    public TicketValidationController(TicketValidationService ticketValidationService) {
        this.ticketValidationService = ticketValidationService;
    }


    @CrossOrigin
    @PostMapping("code")
    @ApiOperation(value = "Ticket verification", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "ERROR"),
            @ApiResponse(code = 200, message = "OK")})
    ResponseEntity<?> get(@RequestBody TicketValidationDto request){
        return ticketValidationService.validateTicket(request);
    }
}