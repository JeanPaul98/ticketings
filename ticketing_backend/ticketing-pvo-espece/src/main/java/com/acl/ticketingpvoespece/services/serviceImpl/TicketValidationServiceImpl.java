package com.acl.ticketingpvoespece.services.serviceImpl;

import com.acl.ticketingpvoespece.model.Paiement;
import com.acl.ticketingpvoespece.playload.ApiResponseModel;
import com.acl.ticketingpvoespece.repositories.PaiementRepository;
import com.acl.ticketingpvoespece.dto.TicketValidationDto;
import com.acl.ticketingpvoespece.dto.ResponseTicketValidationDto;
import com.acl.ticketingpvoespece.services.TicketValidationService;
import com.acl.ticketingpvoespece.services.UtilService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketValidationServiceImpl implements TicketValidationService {

    private final PaiementRepository paiementRepository;
    private final UtilService utilService;

    public TicketValidationServiceImpl(PaiementRepository paiementRepository, UtilService utilService) {
        this.paiementRepository = paiementRepository;
        this.utilService = utilService;
    }

    @Override
    public ResponseEntity<?> validateTicket(TicketValidationDto request) {

        String codeTicket = request.getCodeTicket();
        Optional<Paiement> payment = paiementRepository.findByCodeTicket(codeTicket);
        ResponseTicketValidationDto responseTicketValidationDto = new ResponseTicketValidationDto();


        if(payment.isPresent()){
            if (utilService.compareDate(payment.get().getDateExpiration())){

                responseTicketValidationDto.setCodeTicket(payment.get().getCodeTicket());
                responseTicketValidationDto.setExpirationTime(payment.get().getDateExpiration());
                return new ResponseEntity<>(new ApiResponseModel(HttpStatus.OK.name(),
                        "Ticket valide", responseTicketValidationDto),HttpStatus.OK);
            }else{
                responseTicketValidationDto.setCodeTicket(payment.get().getCodeTicket());
                responseTicketValidationDto.setExpirationTime(payment.get().getDateExpiration());
                return new ResponseEntity<>(new ApiResponseModel(HttpStatus.OK.name(),
                        "Ticket non valide", responseTicketValidationDto),HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                    "ticket not found"), HttpStatus.OK);
        }


    }

}
