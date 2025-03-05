package com.acl.pvoadministration.controller;

import com.acl.pvoadministration.playload.ApiResponseModel;
import com.acl.pvoadministration.playload.TicketValidationRequest;
import com.acl.pvoadministration.services.TicketValidationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "api/admin/ticket", produces = APPLICATION_JSON_VALUE)
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
    ResponseEntity<?> getTicket(@RequestBody TicketValidationRequest request){
        return ticketValidationService.validateTicket(request);
    }
}
