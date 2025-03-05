package com.acl.ticketingauth.repositories;

import com.acl.ticketingauth.model.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Zansouyé
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByCode(String code);

    @Query(value = "select * from ticketing_role where actif=1 ", nativeQuery = true)
    List<Role> listRolesActif();
}
