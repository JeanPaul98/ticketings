package com.acl.ticketinggateway.service.administration;

import com.acl.ticketinggateway.dto.PrestationDto;
import com.acl.ticketinggateway.dto.TypePrestationDto;
import com.acl.ticketinggateway.playload.ApiResponseModel;
import com.acl.ticketinggateway.request.TicketValidationRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(name = "ticketing-pvo-administration")
public interface AdministrationService {


    @PostMapping("/api/prestation")
    @Retry(name = "ticketing-pvo-administration")
    @CircuitBreaker(name = "ticketing-pvo-administration", fallbackMethod = "getAuthentication")
    ResponseEntity<?> createPrestation(PrestationDto prestationDto);

    @GetMapping("/api/prestation/detail/{designation}")
    @Retry(name = "ticketing-pvo-administration")
    @CircuitBreaker(name = "ticketing-pvo-administration", fallbackMethod = "getAuthentication")
    ResponseEntity<?> getPrestationByDesignation(@RequestParam String designation);

    /**
     * Recupération de prestation par désignation
     * @return
     */
    @GetMapping("/api/prestation/list")
    @Retry(name = "ticketing-pvo-administration")
    @CircuitBreaker(name = "ticketing-pvo-administration", fallbackMethod = "getAuthentication")
    ResponseEntity<?> getAllPrestations();


    @PostMapping("/api/typePrestation")
    @Retry(name = "ticketing-pvo-administration")
    @CircuitBreaker(name = "ticketing-pvo-administration", fallbackMethod = "getAuthentication")
    ResponseEntity<?> createTypePrestation(TypePrestationDto typePrestationDto);

    @GetMapping("/api/typePrestation/list")
    @Retry(name = "ticketing-pvo-administration")
    @CircuitBreaker(name = "ticketing-pvo-administration", fallbackMethod = "getAuthentication")
    ResponseEntity<?> getAllTypePrestations();


    @PostMapping("api/admin/ticket/code")
    @Retry(name = "ticketing-pvo-administration")
    @CircuitBreaker(name = "ticketing-pvo-administration", fallbackMethod = "getAuthentication")
    ResponseEntity<?> getTicket(@RequestBody TicketValidationRequest request);



    @GetMapping("api/payments/list")
    @Retry(name = "ticketing-pvo-administration")
    @CircuitBreaker(name = "ticketing-pvo-administration", fallbackMethod = "getAuthentication")
    ResponseEntity<?> getTransaction();


    @GetMapping("api/payments/list/day")
    @Retry(name = "ticketing-pvo-administration")
    @CircuitBreaker(name = "ticketing-pvo-administration", fallbackMethod = "getAuthentication")
    ResponseEntity<?> getTransactionDay();


    @GetMapping("api/payments/search")
    @Retry(name = "ticketing-pvo-administration")
    @CircuitBreaker(name = "ticketing-pvo-administration", fallbackMethod = "getAuthentication")
    ResponseEntity<?> searchTransaction(@RequestParam String searchValue);


    @GetMapping("api/payments/date/search")
    @Retry(name = "ticketing-pvo-administration")
    @CircuitBreaker(name = "ticketing-pvo-administration", fallbackMethod = "getAuthentication")
    ResponseEntity<?> filter(@RequestParam String dateDebut,@RequestParam String dateFin);

    @GetMapping("api/payments/date/filter")
    @Retry(name = "ticketing-pvo-administration")
    @CircuitBreaker(name = "ticketing-pvo-administration", fallbackMethod = "getAuthentication")
    ResponseEntity<?> filterDayAndSearch(@RequestParam String dateDebut,@RequestParam String dateFin,@RequestParam String searchValue);


    @GetMapping(value = "api/admin/report/coris",produces = MediaType.APPLICATION_PDF_VALUE)
    @Retry(name = "ticketing-pvo-administration")
    @CircuitBreaker(name = "ticketing-pvo-administration", fallbackMethod = "getAuthentication")
    ResponseEntity<byte[]> reportingCoris(@RequestParam String dateDebut,@RequestParam String dateFin);



    default ResponseEntity<?> getAuthentication( Throwable exception) {
        System.out.println("Message "+ exception.getMessage());
        return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                "Opération Failed"), HttpStatus.OK);
    }

}
