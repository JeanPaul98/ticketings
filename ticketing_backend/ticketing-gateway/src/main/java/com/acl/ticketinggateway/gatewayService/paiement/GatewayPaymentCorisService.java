package com.acl.ticketinggateway.gatewayService.paiement;

import com.acl.ticketinggateway.playload.CorisPaymentRequest;
import com.acl.ticketinggateway.playload.UserRequest;
import com.acl.ticketinggateway.request.PaiementRequest;
import com.acl.ticketinggateway.request.PaiementRequestOtp;
import com.acl.ticketinggateway.service.paiement.coris.PaymentCorisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatewayPaymentCorisService {

    private final PaymentCorisService service;

    public ResponseEntity<?> payment(PaiementRequest request) {
        return service.payment(request);
    }

    public ResponseEntity<?> otp(PaiementRequestOtp paiementRequestOtp) {
        return service.checkOtp(paiementRequestOtp);
    }


    public ResponseEntity<?> otpPesage(PaiementRequestOtp paiementRequestOtp) {
        return service.checkOtpPesage(paiementRequestOtp);
    }
}
