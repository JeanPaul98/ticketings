package com.acl.ticketingpvoespece.services;


import com.acl.ticketingpvoespece.model.Paiement;
import org.apache.http.client.HttpClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;


@Service
public interface UtilService {

   boolean manupulateTelephone(String telephone);
   HttpClient httpClient();
   String generateTicketCode();
   String generateRandomId();

   String generateReference(int lengh);
   String hash256(String param) throws Exception;
   boolean compareDate(Date dateExpriration);
   Date ajoutDate();

   LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert);

}
