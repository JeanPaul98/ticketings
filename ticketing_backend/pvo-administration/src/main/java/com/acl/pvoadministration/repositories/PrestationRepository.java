package com.acl.pvoadministration.repositories;
import com.acl.pvoadministration.model.Prestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PrestationRepository extends JpaRepository<Prestation, Long> {

    Optional<Prestation> findByDesignation(String designation);
}