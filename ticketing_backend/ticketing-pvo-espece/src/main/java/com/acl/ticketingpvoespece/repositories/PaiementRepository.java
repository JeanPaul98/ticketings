package com.acl.ticketingpvoespece.repositories;


import com.acl.ticketingpvoespece.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
   Optional <Paiement> findByCodeTicket(String codeTicket);
}