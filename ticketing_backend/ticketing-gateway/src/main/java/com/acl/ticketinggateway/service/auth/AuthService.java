package com.acl.ticketinggateway.service.auth;

import com.acl.ticketinggateway.dto.AjoutRoleUserDto;
import com.acl.ticketinggateway.dto.RoleDto;
import com.acl.ticketinggateway.request.LoginRequest;
import com.acl.ticketinggateway.playload.ApiResponseModel;
import com.acl.ticketinggateway.request.RegisterRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ticketing-auth")
public interface AuthService {

    @PostMapping("/api/auth/login/tpe")
    @Retry(name = "ticketing-auth")
    @CircuitBreaker(name = "ticketing-auth", fallbackMethod = "getAuthentication")
    ResponseEntity<?> loginTpe(LoginRequest loginRequest);

    @PostMapping("/api/auth/login/web")
    @Retry(name = "ticketing-auth")
    @CircuitBreaker(name = "ticketing-auth", fallbackMethod = "getAuthentication")
    ResponseEntity<?> loginWeb(LoginRequest loginRequest);

    @PostMapping("/api/auth/register")
    @Retry(name = "ticketing-auth")
    @CircuitBreaker(name = "ticketing-auth", fallbackMethod = "getAuthentication")
    ResponseEntity<?> registerUser(RegisterRequest loginRequest);

    @GetMapping("/api/role")
    @Retry(name = "ticketing-auth")
    @CircuitBreaker(name = "ticketing-auth", fallbackMethod = "getAuthentication")
    ResponseEntity<?>getAllRoles();

    @PostMapping("/api/role/create")
    @Retry(name = "ticketing-auth")
    @CircuitBreaker(name = "ticketing-auth", fallbackMethod = "getAuthentication")
    ResponseEntity<?>createRole(RoleDto roleDto);

    @PostMapping("/api/role/disable")
    @Retry(name = "ticketing-auth")
    @CircuitBreaker(name = "ticketing-auth", fallbackMethod = "getAuthentication")
    ResponseEntity<?>desactiverRole(AjoutRoleUserDto roleDto);

    default ResponseEntity<?> getAuthentication(LoginRequest loginRequest, Throwable exception) {
        System.out.println("Utilisateur " + loginRequest.getUsername());
        System.out.println("Message "+ exception.getMessage());
        return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                "Authentification failed"), HttpStatus.OK);
    }


}
