package com.acl.ticketingpvoespece.services.serviceImpl;


import com.acl.ticketingpvoespece.converter.PaiementConverter;
import com.acl.ticketingpvoespece.dto.PrestationDto;
import com.acl.ticketingpvoespece.dto.TicketingRecettesDto;
import com.acl.ticketingpvoespece.enums.TypePaiement;
import com.acl.ticketingpvoespece.model.Paiement;
import com.acl.ticketingpvoespece.model.Prestation;
import com.acl.ticketingpvoespece.model.TypePrestation;
import com.acl.ticketingpvoespece.playload.ApiResponseModel;
import com.acl.ticketingpvoespece.repositories.PaiementRepository;
import com.acl.ticketingpvoespece.repositories.PrestationRepository;
import com.acl.ticketingpvoespece.repositories.TypePrestationRepository;
import com.acl.ticketingpvoespece.repositories.UserRepository;
import com.acl.ticketingpvoespece.dto.PaiementDto;
import com.acl.ticketingpvoespece.security.CurrentUser;
import com.acl.ticketingpvoespece.security.impl.UserPrincipal;
import com.acl.ticketingpvoespece.services.PaiementService;
import com.acl.ticketingpvoespece.services.UtilService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
public class PaiementServiceImpl implements PaiementService {

    private final Logger logger = LoggerFactory.getLogger(PaiementServiceImpl.class);

    @Autowired
    private  Environment env;
    private final UtilService utilService;
    private final UserRepository userRepository;
    private final PaiementRepository paiementRepository;
    private final PrestationRepository prestationRepository;
    private final TypePrestationRepository typePrestationRepository;

    private final String utf8 = "UTF-8";

    public PaiementServiceImpl(UtilService utilService, UserRepository userRepository, PaiementRepository paiementRepository, PrestationRepository prestationRepository,
                               TypePrestationRepository typePrestationRepository) {

        this.utilService = utilService;
        this.userRepository = userRepository;
        this.paiementRepository = paiementRepository;
        this.prestationRepository = prestationRepository;
        this.typePrestationRepository = typePrestationRepository;
    }

    @Override
    public ResponseEntity<?> validerpaiementespece(PrestationDto prestationDto, @CurrentUser UserPrincipal currentUser) throws IOException {
            logger.info("Paiement espece transaction {} ", prestationDto);
            return initPaiement(prestationDto, currentUser);
    }


    private ResponseEntity<?> initPaiement(PrestationDto prestationDto,
                                           @CurrentUser UserPrincipal currentUser) throws IOException {
        logger.info("Pvo choisi pour le paiement {} ", prestationDto.getDesignation());

        Optional<Prestation> prestationOptional = prestationRepository.
                findPrestationByDesignation(prestationDto.getDesignation());
        if (prestationOptional.isPresent()) {
            Paiement paiement = new Paiement(
                    utilService.generateTicketCode(),
                    utilService.generateReference(10),
                    getMontantTypePrestation(prestationDto.getDesignation()),
                    utilService.generateRandomId(),
                    env.getProperty("app.tpe"),
                    userRepository.findByUsername(currentUser.getUsername()).get().getTelephone(),
                    utilService.ajoutDate(),
                    new Timestamp(System.currentTimeMillis()),
                    TypePaiement.ESPECE,
                    userRepository.findByUsername(currentUser.getUsername()).get()

            );
            logger.info("Finalisation du paiement {} ", paiement);
            Paiement savePaiement= paiementRepository.save(paiement);
            PaiementDto paiementDto = new PaiementDto();
            PaiementConverter paiementConverter = new PaiementConverter();
            paiementDto = paiementConverter.convertDto(savePaiement);
            paiementDto.setUsername(savePaiement.getUser().getUsername());

            //Insertion dans la bdd du PAL
            TicketingRecettesDto ticketingRecettesDto= new TicketingRecettesDto();
            ticketingRecettesDto.setCodeTicket(savePaiement.getCodeTicket());
            ticketingRecettesDto.setTelephone(savePaiement.getTelephone());
            ticketingRecettesDto.setReference(savePaiement.getReference());
            ticketingRecettesDto.setMontant(savePaiement.getMontant());
            ticketingRecettesDto.setOperateur(savePaiement.getOperateur());
            ticketingRecettesDto.setTransactionID(savePaiement.getTransactionId());

            insertBddPAL(ticketingRecettesDto);

            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.CREATED.name(),
                    "Opération Réussie", paiementDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                    "Opération échouée"), HttpStatus.OK);
        }
    }

    /**
     * Envoi du montant de la prestation
     *
     * @param designation
     * @return
     */
    private double getMontantTypePrestation(String designation) {
        Optional<TypePrestation> typePrestation = typePrestationRepository.findTypePrestationByPrestation(designation);
        logger.info("Montant du type de prestation : {} ", typePrestation.get().getPrixUnitaire());
        return typePrestation.map(TypePrestation::getPrixUnitaire).orElse(0.0);
    }

    public void insertBddPAL(TicketingRecettesDto ticketingRecettesDto) throws IOException {
        HttpClient httpClient = utilService.httpClient();

        String jsonTicketingNotif = "{\n"
                + "    \"codeTicket\": " + "\"" + ticketingRecettesDto.getCodeTicket() + "\" " + ",\n"
                + "    \"reference\": " + "\"" + ticketingRecettesDto.getReference() + "\" " + ",\n"
                + "    \"transactionID\": " + "\"" + ticketingRecettesDto.getTransactionID() + "\" " + ",\n"
                + "    \"telephone\": " + "\"" + ticketingRecettesDto.getTelephone() + "\" " + ",\n"
                + "    \"operateur\": " + "\"" + ticketingRecettesDto.getOperateur() + "\" " + ",\n"
                + "    \"montant\": " + "\"" + ticketingRecettesDto.getMontant() + "\"\n"
                + "}";


        logger.info("Ticketing Notification "+jsonTicketingNotif);

        HttpUriRequest requests = RequestBuilder.post()
                .setUri(env.getProperty("app.notif.pal"))
                .setEntity(new StringEntity(jsonTicketingNotif))
                .setHeader("Content-Type", "application/json")
                .build();

        //HttpResponse response = httpClient.execute(request);
        HttpResponse response = httpClient.execute(requests);

        // Get the http status code
        int statusCode = response.getStatusLine().getStatusCode();

        //System.out.println("statusCode airtel Kin Tampon " + statusCode);
        logger.info("Notification PAL: " + statusCode);

        // Get the response message
        String responseBody = EntityUtils.toString(response.getEntity(), utf8);

        //System.out.println(" responseBody callbackAirtel Kin Tampon " + responseBody);
        logger.info("ResponseBody Notification PAL " + responseBody);

        if (statusCode == 200) {

            logger.info(" Enregistrement dans la base de notification du PAL " );
        }
    }

}
