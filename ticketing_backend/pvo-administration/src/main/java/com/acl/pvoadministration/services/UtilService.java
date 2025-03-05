package com.acl.pvoadministration.services;

import com.acl.pvoadministration.model.Paiement;
import org.springframework.stereotype.Service;

@Service
public interface UtilService {
    boolean isTicketValid(Paiement payment);
}
