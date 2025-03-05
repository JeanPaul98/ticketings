package com.acl.ticketingpvoespece.services.serviceImpl;

import com.acl.ticketingpvoespece.services.UtilService;
import com.google.common.hash.Hashing;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UtilServiceImpl implements UtilService {
    private static final SecureRandom RANDOM = new SecureRandom();

  private Pattern p = Pattern.compile("^(\\d{1,3})\\d{8}$");

  @Autowired
  private Environment env;

    @Override
    public boolean manupulateTelephone(String telephone) {
        Matcher m = p.matcher(telephone);

        return m.matches();
    }

    @Override
    public HttpClient httpClient() {
        try {
            return  HttpClients
                    .custom()
                    .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null,
                            TrustAllStrategy.INSTANCE).build())
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateTicketCode() {
        int ticketCodeLength = 16;
        StringBuilder sb = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        Random RANDOM = new SecureRandom();
        String characters = this.env.getProperty("app.characters");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MMdd.");
        sb.append("MT").append(now.format(formatter));
        for (int i = 0; i < ticketCodeLength - 10; i++) {
            assert characters != null;
            sb.append(characters.charAt(RANDOM.nextInt(characters.length())));
        }
        return sb.toString();
    }

    @Override
    public String generateRandomId() {
        Random rand = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        Date newDate = new Date();
        return sdf.format(newDate);
    }

    @Override
    public String generateReference(int length) {
            Random random = new Random();
            StringBuilder sb = new StringBuilder(length);
        String characters = this.env.getProperty("app.characters");
        for (int i = 0; i < length; i++) {
                sb.append(characters.charAt(random.nextInt(characters.length())));
            }
            return sb.toString();

    }

    @Override
    public String hash256(String param) throws Exception {
        String sha256hex = Hashing.sha256().hashString(param, StandardCharsets.UTF_8).toString();
        return sha256hex;
    }

    @Override
    public boolean compareDate(Date dateExpiration) {

        LocalDateTime localDateTimeExpire= convertToLocalDateTimeViaInstant(dateExpiration);
        LocalDateTime now= LocalDateTime.now();
        return now.equals(localDateTimeExpire);


       /* Calendar calendarExpiration = Calendar.getInstance();
        calendarExpiration.setTime(dateExpiration);
        int expirationDay = calendarExpiration.get(Calendar.DAY_OF_YEAR);

        Calendar calendarNow = Calendar.getInstance();
        int nowDay = calendarNow.get(Calendar.DAY_OF_YEAR);

        return nowDay < expirationDay;*/
    }

    @Override
    public Date ajoutDate() {
        LocalDateTime dateExpiration = LocalDateTime.now().plusDays(1);
        Instant instant = dateExpiration.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    @Override
    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

}
