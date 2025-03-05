package com.acl.ticketingpvoespece.services;


import com.acl.ticketingpvoespece.dto.PrestationDto;
import com.acl.ticketingpvoespece.security.CurrentUser;
import com.acl.ticketingpvoespece.security.impl.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface PaiementService {
    ResponseEntity<?> validerpaiementespece(PrestationDto prestationDto,
                                            @CurrentUser UserPrincipal currentUser) throws IOException;
}
