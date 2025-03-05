package com.acl.pvoadministration.services.ServiceImpl;

import com.acl.pvoadministration.dao.TicketValidationDao;
import com.acl.pvoadministration.model.Paiement;
import com.acl.pvoadministration.playload.ApiResponseModel;
import com.acl.pvoadministration.playload.TicketValidationRequest;
import com.acl.pvoadministration.repositories.PaiementRepository;
import com.acl.pvoadministration.responses.TicketValidationResponse;
import com.acl.pvoadministration.services.TicketValidationService;
import com.acl.pvoadministration.services.UtilService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TicketValidationServiceImpl implements TicketValidationService {

    private final PaiementRepository paiementRepository;
    private final UtilService utilService;
    public TicketValidationServiceImpl(PaiementRepository paiementRepository, UtilService utilService) {
        this.paiementRepository = paiementRepository;
        this.utilService = utilService;
    }

    @Override
    public ResponseEntity<?> validateTicket(TicketValidationRequest request) {
        String codeTicket = request.getCodeTicket();
        Paiement payment = paiementRepository.findByCodeTicket(codeTicket);
        TicketValidationDao ticketValidationDao = new TicketValidationDao();

        if (payment == null) {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                    "ticket not found"), HttpStatus.OK);
        }

        if (utilService.isTicketValid(payment) == true){

            ticketValidationDao.setCodeTicket(payment.getCodeTicket());
            ticketValidationDao.setExpirationTime(payment.getDateExpiration());
            ticketValidationDao.setStatus(true);
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.OK.name(),
                    "Ticket valide", ticketValidationDao),HttpStatus.OK);
        }else{
            ticketValidationDao.setCodeTicket(payment.getCodeTicket());
            ticketValidationDao.setExpirationTime(payment.getDateExpiration());
            ticketValidationDao.setStatus(false);
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.OK.name(),
                    "Ticket non valide", ticketValidationDao),HttpStatus.OK);
        }
    }
}
