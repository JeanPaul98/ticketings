package com.acl.ticketingpvocoris.services.coris.impls;

import com.acl.ticketingpvocoris.dto.TicketingRecettesDto;
import com.acl.ticketingpvocoris.enums.TypePaiement;
import com.acl.ticketingpvocoris.model.Paiement;
import com.acl.ticketingpvocoris.model.Prestation;
import com.acl.ticketingpvocoris.model.TypePrestation;
import com.acl.ticketingpvocoris.playload.ApiResponseModel;
import com.acl.ticketingpvocoris.repositories.PaiementRepository;
import com.acl.ticketingpvocoris.repositories.PrestationRepository;
import com.acl.ticketingpvocoris.repositories.TypePrestationRepository;
import com.acl.ticketingpvocoris.repositories.UserRepository;
import com.acl.ticketingpvocoris.requests.PaiementRequestOtp;
import com.acl.ticketingpvocoris.requests.CheckOtpResponse;
import com.acl.ticketingpvocoris.security.CurrentUser;
import com.acl.ticketingpvocoris.security.impl.UserPrincipal;
import com.acl.ticketingpvocoris.services.UtilService;
import com.acl.ticketingpvocoris.services.coris.OtpService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;

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
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class OtpServiceImpl implements OtpService {

    private final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);
    private final UtilService utilService;
    private final TypePrestationRepository typePrestationRepository;
    private final PaiementRepository paiementRepository;
    private final UserRepository userRepository;

    private final PrestationRepository prestationRepository;
    private final String utf8 = "UTF-8";

    @Autowired
    private Environment env;

    public OtpServiceImpl(UtilService utilService, TypePrestationRepository typePrestationRepository,
                          PaiementRepository paiementRepository, UserRepository userRepository,
                          PrestationRepository prestationRepository) {
        this.utilService = utilService;
        this.typePrestationRepository = typePrestationRepository;
        this.paiementRepository = paiementRepository;
        this.userRepository = userRepository;
        this.prestationRepository = prestationRepository;
    }

    /**
     * Vérification de l'OTP
     * @param currentUser
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ResponseEntity<?> checkCodeOtp(@CurrentUser UserPrincipal currentUser, PaiementRequestOtp request) throws Exception {
        logger.info("Utilisateur connecté {}  ", currentUser.getNom());
        if (!utilService.manupulateTelephone(request.getCountryCode() +
                request.getPhone())) {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                    "Numéro de téléphone non valide"), HttpStatus.OK);

        } else {
            String clientSecret = this.env.getProperty("app.clientSecret");
            String clientId = this.env.getProperty("app.clientId");
            String codePv = this.env.getProperty("app.codePv");
            double montant = getMontantTypePrestation(request.getPrestation());

            String hashParam = request.getCountryCode() + request.getPhone() + codePv +
                    montant + request.getCodeOtp() + clientSecret;
            logger.info("hash params {}  ", hashParam);
            String hash = utilService.hash256(hashParam);
            return verifyOtpAndPaiement(request, clientId, hash , currentUser);
        }

    }
    /**
     * Lancer le paiement
     * @param paiementRequestOtp
     * @param clientId
     * @param hashParam
     * @param currentUser
     * @return
     * @throws IOException
     */
    private ResponseEntity<?> verifyOtpAndPaiement(PaiementRequestOtp paiementRequestOtp,
                                                   String clientId, String hashParam,
                                                   @CurrentUser UserPrincipal currentUser) throws IOException {
        HttpClient httpClient = utilService.httpClient();
        String url = this.env.getProperty("app.coris.paiement.url");
        String codePv = this.env.getProperty("app.codePv");

        HttpUriRequest requests = RequestBuilder.post()
                .setUri(url + "paiement-bien?codePays=" +paiementRequestOtp.getCountryCode()
                                +"&telephone="+paiementRequestOtp.getPhone()+"&codePv="+codePv
                        +"&montant="+getMontantTypePrestation(paiementRequestOtp.getPrestation())
                        +"&codeOTP="+paiementRequestOtp.getCodeOtp())
                .setHeader("hashParam", hashParam)
                .setHeader("clientId", clientId)
                .setHeader("Content-Type", MediaType.APPLICATION_JSON)
                .build();

        HttpResponse response = httpClient.execute(requests);
        int statusCode = response.getStatusLine().getStatusCode();
        logger.info("Paiement: Url {} ", requests.getURI().toString());

        String responseBody = EntityUtils.toString(response.getEntity(), utf8);
        logger.info("Paiement: statusCode  " + statusCode);

        ObjectMapper objectMapper = new ObjectMapper();
        CheckOtpResponse checkOtpResponse = new CheckOtpResponse();
        if (statusCode == 200) {
            checkOtpResponse = objectMapper.readValue(responseBody, CheckOtpResponse.class);
            logger.info("Paiement: Otp response {}  ", responseBody);
            return savePaiement(paiementRequestOtp, checkOtpResponse, currentUser);

        } else {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_ACCEPTABLE.name(),
                    "Opération échouée", responseBody), HttpStatus.OK);
        }

    }

    /**
     * Vérification du montant
     * @param designation
     * @return
     */
    private double getMontantTypePrestation(String designation) {
        Optional<TypePrestation> typePrestation = typePrestationRepository.findTypePrestationByPrestation(designation);
        return typePrestation.map(TypePrestation::getPrixUnitaire).orElse(0.0);
    }

    /**
     * Enregistrement du paiement dans la base
     * @param paiementRequestOtp
     * @param checkOtpResponse
     * @param currentUser
     * @return
     */
    private ResponseEntity<?> savePaiement(PaiementRequestOtp paiementRequestOtp ,
                                           CheckOtpResponse checkOtpResponse,
                                           @CurrentUser UserPrincipal currentUser ) throws IOException {
        if(checkOtpResponse.getCode().equals("0")) {
           return  insertBdd(paiementRequestOtp,checkOtpResponse,currentUser );
        } else {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_ACCEPTABLE.name(),
                    checkOtpResponse.getMessage(), checkOtpResponse.getCode()), HttpStatus.OK);
        }

    }

    /**
     * Insertion dans la bdd de paiement
     * @param paiementRequestOtp
     * @param checkOtpResponse
     * @param currentUser
     * @return
     */
    private ResponseEntity<?> insertBdd(PaiementRequestOtp paiementRequestOtp,CheckOtpResponse checkOtpResponse,
                                              @CurrentUser UserPrincipal currentUser) throws IOException {
        Optional<Prestation> prestationOptional = prestationRepository.
                findPrestationByDesignation(paiementRequestOtp.getPrestation());

        if(prestationOptional.isPresent()) {
            Paiement paiement = new Paiement();
            paiement.setCodeTicket(utilService.generateTicketCode());
            paiement.setReference(checkOtpResponse.getTransactionId());
            paiement.setMontant(checkOtpResponse.getMontant());
            paiement.setOperateur(env.getProperty("app.coris.name"));
            paiement.setUser(userRepository.findByUsername(currentUser.getUsername()).get());
            paiement.setTelephone(paiementRequestOtp.getPhone());
            paiement.setTypePaiement(TypePaiement.MOBILE);
            paiement.setTransactionId(utilService.generateRandomId());
            paiement.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            paiement.setDateExpiration(utilService.ajoutDate());
            Paiement savePaiement= paiementRepository.save(paiement);
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
                    "Opération Réussie", savePaiement), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                    "Opération échouée"), HttpStatus.OK);
        }
    }

    public void insertBddPAL(TicketingRecettesDto ticketingRecettesDto) throws IOException {
        HttpClient httpClient = utilService.httpClient();

        String jsonTicketingNotif = "{\n"
                + "    \"codeTicket\": " + "\"" + ticketingRecettesDto.getCodeTicket() + "\" " + ",\n"
                + "    \"reference\": " + "\"" + ticketingRecettesDto.getReference() + "\" " + ",\n"
                + "    \"transactionID\": " + "\"" + ticketingRecettesDto.getTransactionID() + "\" " + ",\n"
                + "    \"telephone\": " + "\"" + ticketingRecettesDto.getTelephone() + "\" " + ",\n"
                + "    \"operateur\": " + "\"" + ticketingRecettesDto.getOperateur() + "\" " + ",\n"
                + "    \"montant\": " + "\"" + ticketingRecettesDto.getMontant() + "\" \n"
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