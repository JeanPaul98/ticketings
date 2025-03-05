package com.acl.ticketinggateway.gatewayService.administration;

import com.acl.ticketinggateway.dto.PrestationDto;
import com.acl.ticketinggateway.dto.TypePrestationDto;
import com.acl.ticketinggateway.gatewayService.auth.GatewayAuthentificationService;
import com.acl.ticketinggateway.request.TicketValidationRequest;
import com.acl.ticketinggateway.service.administration.AdministrationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Service
public class GatewayAdministrationService {

    final Logger logger = LoggerFactory.getLogger(GatewayAuthentificationService.class);

    private final AdministrationService administrationService;

    public GatewayAdministrationService(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    /**
     * Création de prestation
     * @param prestationDto
     * @return
     */
    public ResponseEntity<?> createPrestation(PrestationDto prestationDto) {
        return administrationService.createPrestation(prestationDto);
    }

    /**
     * Liste tous les prestations
     * @return
     */
    public ResponseEntity<?> getAllPrestations() {
        return administrationService.getAllPrestations();
    }

    /**
     * Création de prestation
     * @param designation
     * @return
     */
    public ResponseEntity<?> findPrestationByDesignation(String designation) {
        return administrationService.getPrestationByDesignation(designation);
    }

    /**
     * Création de prestation
     * @param typePrestationDto
     * @return
     */
    public ResponseEntity<?> createTypePrestation(TypePrestationDto typePrestationDto) {
        return administrationService.createTypePrestation(typePrestationDto);
    }

    /**
     * Liste tous les prestations
     * @return
     */
    public ResponseEntity<?> getAllTyPrestations() {
        return administrationService.getAllTypePrestations();
    }


    /**
     * Verification ticket
     * @return
     */
    public ResponseEntity<?> getTicket(@RequestBody TicketValidationRequest request){
        return administrationService.getTicket(request);

    }

    /**
     * Liste des transactions de la journée
     * @return
     */
    public ResponseEntity<?> getTransactionDay(){
        return administrationService.getTransactionDay();
    }

    /**
     * Liste tous les transactions par page
     * @return
     */
    public ResponseEntity<?> getTransaction(){
        return administrationService.getTransaction();
    }

    /**
     * search tous les transactions
     * @return
     */
    public ResponseEntity<?> searchTransaction(String searchValue){
        return administrationService.searchTransaction(searchValue);
    }


    /**
     * filter tous les transactions par date debut et date fin
     * @return
     */
    public ResponseEntity<?> filterTransaction(String dateDebut, String dateFin){
        return administrationService.filter(dateDebut,dateFin);
    }


    /**
     * filter tous les transactions
     * @return
     */
    public ResponseEntity<?> filterDayAndSearch(String dateDebut,String dateFin,String searchValue){
        return administrationService.filterDayAndSearch(dateDebut,dateFin,searchValue);
    }


    /**
     * Reporting des transactions
     * @return
     */
    public ResponseEntity<byte[]> reportingCoris(String dateDebut,String dateFin){
        return administrationService.reportingCoris(dateDebut,dateFin);
    }
}
