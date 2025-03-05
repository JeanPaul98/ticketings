package com.acl.pvoadministration.repositories;


import com.acl.pvoadministration.model.TypePrestation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypePrestationRepository extends JpaRepository<TypePrestation, Long> {
    Page<TypePrestation> findAll(Pageable pageable);

    Optional<TypePrestation> findTypePrestationByDesignation(String designation);
}