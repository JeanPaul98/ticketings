package com.acl.ticketingpvocoris.services;


import org.springframework.stereotype.Service;
import org.apache.http.client.HttpClient;

import java.util.Date;


@Service
public interface UtilService {

   boolean manupulateTelephone(String telephone);
   HttpClient httpClient();
   String generateTicketCode();
   String generateRandomId();
   String hash256(String param) throws Exception;

   Date ajoutDate();

}
