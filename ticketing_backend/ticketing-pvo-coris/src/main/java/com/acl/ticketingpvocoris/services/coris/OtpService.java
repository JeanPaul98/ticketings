package com.acl.ticketingpvocoris.services.coris;

import com.acl.ticketingpvocoris.requests.PaiementRequestOtp;
import com.acl.ticketingpvocoris.security.CurrentUser;
import com.acl.ticketingpvocoris.security.impl.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OtpService {
    ResponseEntity<?> checkCodeOtp(@CurrentUser UserPrincipal currentUser, PaiementRequestOtp paiementRequestOtp)
            throws Exception;
}
