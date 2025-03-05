package com.acl.ticketingpvoespece.controller;

import com.acl.ticketingpvoespece.dto.PrestationDto;
import com.acl.ticketingpvoespece.playload.ApiResponseModel;
import com.acl.ticketingpvoespece.security.CurrentUser;
import com.acl.ticketingpvoespece.security.impl.UserPrincipal;
import com.acl.ticketingpvoespece.services.PaiementService;
import com.acl.ticketingpvoespece.validator.RetrieveValidationError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "api/paiement")
@Api(tags = {"Payment"}, value = "PaiementController Rest Controller: Gérer le paiement espece")
public class PaiementController {
    private final Logger logger= LoggerFactory.getLogger(AuthentificationController.class);
    private final PaiementService paiementService;
    private final RetrieveValidationError retrieveValidationError;

    public PaiementController(PaiementService paiementService, RetrieveValidationError retrieveValidationError) {
        this.paiementService = paiementService;
        this.retrieveValidationError = retrieveValidationError;
    }

    @CrossOrigin
    @PostMapping(value = "cash")
    @ApiOperation(value = "Effectué le paiement", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "ERROR: The cash payment has failed. "),
            @ApiResponse(code = 200, message = "OK: Payment made successfully ")})
    public ResponseEntity<?> cash(@RequestBody @Valid PrestationDto prestationDto,
                                  @CurrentUser UserPrincipal currentUser, BindingResult result) throws IOException {
        logger.info("Lancement du paiement {} ", prestationDto);
        if (result.hasErrors()) {
            return retrieveValidationError.retrieveErrors(result);
        }
        logger.info("Utilisateur {} ", currentUser);
        return paiementService.validerpaiementespece(prestationDto, currentUser);
    }


}
