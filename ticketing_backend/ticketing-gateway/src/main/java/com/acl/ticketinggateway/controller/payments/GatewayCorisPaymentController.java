package com.acl.ticketinggateway.controller.payments;

import com.acl.ticketinggateway.gatewayService.auth.GatewayAuthentificationService;
import com.acl.ticketinggateway.gatewayService.paiement.GatewayPaiementEspeceService;
import com.acl.ticketinggateway.gatewayService.paiement.GatewayPaymentCorisService;
import com.acl.ticketinggateway.playload.ApiResponseModel;
import com.acl.ticketinggateway.request.EspecePaymentRequest;
import com.acl.ticketinggateway.request.PaiementRequest;
import com.acl.ticketinggateway.request.PaiementRequestOtp;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("paiements/coris")
public class GatewayCorisPaymentController {

    final Logger logger = LoggerFactory.getLogger(GatewayAuthentificationService.class);
    private final GatewayPaymentCorisService service;
    private final GatewayPaiementEspeceService especeService;

    public GatewayCorisPaymentController(GatewayPaymentCorisService service,
                                         GatewayPaiementEspeceService especeService) {
        this.service = service;
        this.especeService = especeService;
    }

    /**
     * Paiement CORIS
     *
     * @param
     * @return
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("init")
    @ApiOperation(value = "List ", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: The User is not exist into database"),
            @ApiResponse(code = 401, message = "UNAUTORIZED: The User is not autorize to access  "),
            @ApiResponse(code = 200, message = "OK: The User is  connect  ")})
    public ResponseEntity<?> payment(@RequestBody PaiementRequest request) {
        logger.info("Lancement du paiement " + request);
        return service.payment(request);
    }

    /**
     * Vérification OTP
     *
     * @param
     * @return
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("checkOtp")
    @ApiOperation(value = "List ", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: The User is not exist into database"),
            @ApiResponse(code = 401, message = "UNAUTORIZED: The User is not autorize to access  "),
            @ApiResponse(code = 200, message = "OK: The User is  connect  ")})
    public ResponseEntity<?> checkOtp(@RequestBody PaiementRequestOtp request) {
        logger.info("Check Otp " + request);
        return service.otp(request);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("checkOtp/pesage")
    @ApiOperation(value = "List ", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: The User is not exist into database"),
            @ApiResponse(code = 401, message = "UNAUTORIZED: The User is not autorize to access  "),
            @ApiResponse(code = 200, message = "OK: The User is  connect  ")})
    public ResponseEntity<?> checkOtpPesage(@RequestBody PaiementRequestOtp request) {
        logger.info("Check Otp " + request);
        return service.otpPesage(request);
    }
}
