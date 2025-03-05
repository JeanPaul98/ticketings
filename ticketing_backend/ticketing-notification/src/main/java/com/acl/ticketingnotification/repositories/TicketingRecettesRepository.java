package com.acl.ticketingnotification.repositories;

import com.acl.ticketingnotification.model.TicketingRecettes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Zansouye
 */
@Repository
public interface TicketingRecettesRepository extends JpaRepository<TicketingRecettes,Long> {

}
