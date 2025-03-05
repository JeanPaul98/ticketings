package com.acl.ticketingpvocoris.services.coris.impls;


import com.acl.ticketingpvocoris.playload.ApiResponseModel;
import com.acl.ticketingpvocoris.dto.PaiementDto;
import com.acl.ticketingpvocoris.services.coris.PaiementService;
import com.acl.ticketingpvocoris.services.UtilService;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class PaiementServiceImpl implements PaiementService {

    private final Logger logger = LoggerFactory.getLogger(PaiementServiceImpl.class);
    private final UtilService utilService;
    private final String utf8 = "UTF-8";


    @Autowired
    private Environment env;

    public PaiementServiceImpl(UtilService utilService) {
        this.utilService = utilService;
    }

    /**
     * Lance le traitement
     *
     * @param paiementDto
     * @return
     * @throws Exception
     */
    @Override
    public ResponseEntity<?> initialisationTransaction(PaiementDto paiementDto) throws Exception {

        if (!utilService.manupulateTelephone(paiementDto.getCountryCode() + paiementDto.getPhone())) {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                    "Numéro de téléphone non valide"), HttpStatus.OK);

        } else {
            String clientSecret = this.env.getProperty("app.clientSecret");
            String clientId = this.env.getProperty("app.clientId");

            String hashParam = paiementDto.getCountryCode() + paiementDto.getPhone() +
                    clientSecret;
            String hash = utilService.hash256(hashParam);
            logger.info(" hash params {}", hash);
            return sendOtp(paiementDto, clientId, hash);
        }

    }

    private ResponseEntity<?> sendOtp(PaiementDto paiementDto, String clientId,
                                      String hashParam) throws IOException {
        HttpClient httpClient = utilService.httpClient();
        String url = this.env.getProperty("app.coris.base.url");
        HttpUriRequest requests = RequestBuilder.post()
                .setUri(url + "send-code-otp?codePays=" +
                        paiementDto.getCountryCode() + "&telephone=" + paiementDto.getPhone())
                .setHeader("hashParam", hashParam)
                .setHeader("clientId", clientId)
                .setHeader("Content-Type", MediaType.APPLICATION_JSON)
                .build();
        logger.info("url: " + requests.getURI().toString());
        logger.info("Hash: {} ", hashParam);
        HttpResponse response = httpClient.execute(requests);
        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity(), utf8);
        logger.info("responseBody: {} ", responseBody);
        logger.info("Send OTP: statusCode  " + statusCode);
        if (statusCode == 200) {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.OK.name(),
                    "Opération réussi", paiementDto), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_ACCEPTABLE.name(),
                    "Opération échoué", paiementDto), HttpStatus.OK);
        }

    }


   /* private Paiement getPayment(CorisPaymentRequest request) {
        Paiement payment = new Paiement();
        Prestation prestation = getPrestation(request.getPrestationId());

        payment.setCreatedAt(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(payment.getCreatedAt());
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        payment.setDateExpiration(calendar.getTime());
        payment.setUser(getCashier());
        payment.setCodeTicket(utils.generateRandomString(16));
        payment.setMontant(prestation.getTypePrestation().getPrixUnitaire());
        payment.setTypePaiement(TypePaiement.valueOf(request.getTypePaiement().toUpperCase(Locale.ROOT)));
        return payment;
    }*/


}
