package com.acl.ticketingnotification.services.serviceImpl;

import com.acl.ticketingnotification.controller.TicketingRecettesController;
import com.acl.ticketingnotification.converter.TicketingRecettesConverter;
import com.acl.ticketingnotification.dto.TicketingRecettesDto;
import com.acl.ticketingnotification.model.TicketingRecettes;
import com.acl.ticketingnotification.playload.ApiResponseModel;
import com.acl.ticketingnotification.repositories.TicketingRecettesRepository;
import com.acl.ticketingnotification.services.TicketingRecettesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author Zansouye
 */
@Service
public class TicketingRecettesServiceImpl implements TicketingRecettesService {

    private Logger logger= LoggerFactory.getLogger(TicketingRecettesController.class);
    private final TicketingRecettesRepository ticketingRecettesRepository;

    public TicketingRecettesServiceImpl(TicketingRecettesRepository ticketingRecettesRepository){
        this.ticketingRecettesRepository= ticketingRecettesRepository;
    }
    @Override
    public ResponseEntity<?> insertPaiement(TicketingRecettesDto ticketingRecettesDto) {

        logger.info("ticketingRecettesDto notif "+ticketingRecettesDto);
        if(ticketingRecettesDto.getMontant()==0){
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_ACCEPTABLE.name(),
                    "Montant égal à zéro"), HttpStatus.OK);
        }
        TicketingRecettesConverter ticketingRecettesConverter= new TicketingRecettesConverter();
        TicketingRecettes ticketingRecettes=ticketingRecettesConverter.convertEntity(ticketingRecettesDto);
        ticketingRecettes.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        ticketingRecettes.setDatePaiement(new Timestamp(System.currentTimeMillis()));
        TicketingRecettes saveTicketingRecettes=ticketingRecettesRepository.save(ticketingRecettes);
        return new ResponseEntity<>(new ApiResponseModel(HttpStatus.OK.name(),
                "Opération effectuée avec succès",saveTicketingRecettes), HttpStatus.OK);

    }
}
