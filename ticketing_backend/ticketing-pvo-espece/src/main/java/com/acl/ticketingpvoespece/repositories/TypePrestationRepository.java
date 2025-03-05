package com.acl.ticketingpvoespece.repositories;



import com.acl.ticketingpvoespece.model.TypePrestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypePrestationRepository extends JpaRepository<TypePrestation, Long> {

    @Query("select d from TypePrestation d  where d.prestation.designation = ?1")
    Optional<TypePrestation> findTypePrestationByPrestation(String designation);
}