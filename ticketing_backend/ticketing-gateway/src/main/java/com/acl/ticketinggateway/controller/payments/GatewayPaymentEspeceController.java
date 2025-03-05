package com.acl.ticketinggateway.controller.payments;

import com.acl.ticketinggateway.gatewayService.auth.GatewayAuthentificationService;
import com.acl.ticketinggateway.gatewayService.paiement.GatewayPaiementEspeceService;
import com.acl.ticketinggateway.gatewayService.paiement.GatewayPaymentCorisService;
import com.acl.ticketinggateway.playload.ApiResponseModel;
import com.acl.ticketinggateway.request.EspecePaymentRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payments/espece")
public class GatewayPaymentEspeceController {

    final Logger logger = LoggerFactory.getLogger(GatewayAuthentificationService.class);
    private final GatewayPaiementEspeceService especeService;

    public GatewayPaymentEspeceController(GatewayPaiementEspeceService especeService) {
        this.especeService = especeService;
    }

    /**
     * Connexion de l'utilisateur
     *
     * @param
     * @return
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("init")
    @ApiOperation(value = "Initialisation du paiement par caisse ", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "NOT_FOUND: The User is not exist into database"),
            @ApiResponse(code = 401, message = "UNAUTORIZED: The User is not autorize to access  "),
            @ApiResponse(code = 200, message = "OK: The User is  connect  ")})
    public ResponseEntity<?> payment(@RequestBody EspecePaymentRequest request) {
        logger.info("Lancement du paiement {} " , request);
        return especeService.cash(request);
    }

}
