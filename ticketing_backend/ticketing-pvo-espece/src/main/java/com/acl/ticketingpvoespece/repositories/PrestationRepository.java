package com.acl.ticketingpvoespece.repositories;



import com.acl.ticketingpvoespece.model.Prestation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrestationRepository extends JpaRepository<Prestation, Long> {
    Prestation findByDesignation(String designation);

    Optional<Prestation> findPrestationByDesignation(String designation);
}