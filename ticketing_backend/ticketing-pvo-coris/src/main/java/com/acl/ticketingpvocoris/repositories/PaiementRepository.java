package com.acl.ticketingpvocoris.repositories;


import com.acl.ticketingpvocoris.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    Paiement findByCodeTicket(String codeTicket);
}