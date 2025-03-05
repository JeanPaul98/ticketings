package com.acl.ticketingnotification.controller;

import com.acl.ticketingnotification.dto.TicketingRecettesDto;
import com.acl.ticketingnotification.playload.ApiResponseModel;
import com.acl.ticketingnotification.services.TicketingRecettesService;
import com.acl.ticketingnotification.validator.RetrieveValidationError;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author Zansouye
 */
@RestController
@RequestMapping(value = "api/v1/recettes")
public class TicketingRecettesController {

    private Logger logger= LoggerFactory.getLogger(TicketingRecettesController.class);

    private final TicketingRecettesService ticketingRecettesService;

    private final RetrieveValidationError retrieveValidationError;

    public TicketingRecettesController(TicketingRecettesService ticketingRecettesService,
                                       RetrieveValidationError retrieveValidationError){
        this.ticketingRecettesService=ticketingRecettesService;
        this.retrieveValidationError=retrieveValidationError;
    }

    /**
     * Insertion de la notification de paiement dans la bdd du PAL
     * @param ticketingRecettesDto
     * @return
     */

    @CrossOrigin
    @PostMapping(value = "notif")
    @ApiOperation(value = "Notification de paiement au PAL", response = ApiResponseModel.class)
    @ApiResponses(value = {
                @ApiResponse(code = 406, message = "NOT_ACCEPTABLE: Echec d'insertion"),
            @ApiResponse(code = 200, message = "OK: Insertion réussie")})
    public ResponseEntity<?> insertNotificationPaiement(@RequestBody TicketingRecettesDto ticketingRecettesDto,
                                                        BindingResult result){
        if (result.hasErrors()) {
            return retrieveValidationError.retrieveErrors(result);
        }
        return ticketingRecettesService.insertPaiement(ticketingRecettesDto);
    }
}
