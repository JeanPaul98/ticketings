package com.acl.pvoadministration.services;


import com.acl.pvoadministration.model.Paiement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface PaiementService {

    ResponseEntity<?> listePaiement();
    ResponseEntity<?> findAllTransactionDay();
    ResponseEntity<?> search(String searchValue);
    ResponseEntity<?> searchByDate(Date dateDebut, Date dateFin);
    ResponseEntity<?> searchAndDate(Date dateDebut,Date dateFin,String searchValue);

}
