package com.acl.ticketingpvocoris.controller;

import com.acl.ticketingpvocoris.playload.ApiResponseModel;
import com.acl.ticketingpvocoris.dto.PaiementDto;
import com.acl.ticketingpvocoris.requests.PaiementRequestOtp;
import com.acl.ticketingpvocoris.security.CurrentUser;
import com.acl.ticketingpvocoris.security.impl.UserPrincipal;
import com.acl.ticketingpvocoris.services.coris.OtpPesageService;
import com.acl.ticketingpvocoris.services.coris.PaiementService;
import com.acl.ticketingpvocoris.services.coris.OtpService;
import com.acl.ticketingpvocoris.validator.RetrieveValidationError;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "api/paiements")
public class PaiementController {

    private final Logger logger= LoggerFactory.getLogger(PaiementController.class);


    private final OtpService otpService;

    private final OtpPesageService otpPesageService;

    private final PaiementService paiementService;

    private final RetrieveValidationError retrieveValidationError;

    public PaiementController(OtpService otpService, OtpPesageService otpPesageService, PaiementService paiementService, RetrieveValidationError retrieveValidationError){
        this.otpService=otpService;
        this.otpPesageService = otpPesageService;
        this.paiementService=paiementService;
        this.retrieveValidationError=retrieveValidationError;
    }

    /**
     * Initialisation de la transaction
     *
     * @param paiementDto
     * @param
     * @param result
     * @return
     * @throws Exception
     */
    @CrossOrigin
    @PostMapping(value = "coris")
    @PreAuthorize("hasRole('CAISSIER')")
    @ApiOperation(value = "Initialisation du paiement", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: Echec de paiement"),
            @ApiResponse(code = 200, message = "OK: Lancement de la transaction  ")})
    public ResponseEntity<?> coris(@RequestBody @Valid PaiementDto paiementDto,
                                  BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return retrieveValidationError.retrieveErrors(result);
        }
        return paiementService.initialisationTransaction(paiementDto);
    }

    @CrossOrigin
    @PostMapping(value = "checkotp")
    @ApiOperation(value = "Vérification de l'OTP", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: Echec de paiement"),
            @ApiResponse(code = 200, message = "OK: Paiement effectuer   ")})
    public ResponseEntity<?> otp(@RequestBody PaiementRequestOtp paiementRequestOtp,
                                 @CurrentUser UserPrincipal currentUser,BindingResult result) throws Exception {
       logger.info("Current user {} ", currentUser.getUsername() );
        logger.info("Request user {} ", paiementRequestOtp );

        if (result.hasErrors()) {
            return retrieveValidationError.retrieveErrors(result);
        }
        return otpService.checkCodeOtp(currentUser, paiementRequestOtp);
    }

    @CrossOrigin
    @PostMapping(value = "checkotp/pesage")
    @ApiOperation(value = "Vérification de l'OTP", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: Echec de paiement"),
            @ApiResponse(code = 200, message = "OK: Paiement effectuer   ")})
    public ResponseEntity<?> otpPesage(@RequestBody PaiementRequestOtp paiementRequestOtp,
                                 @CurrentUser UserPrincipal currentUser,BindingResult result) throws Exception {
        logger.info("Current user {} ", currentUser.getUsername() );
        logger.info("Request user {} ", paiementRequestOtp );

        if (result.hasErrors()) {
            return retrieveValidationError.retrieveErrors(result);
        }
        return otpPesageService.checkCodeOtp(currentUser, paiementRequestOtp);
    }

}
