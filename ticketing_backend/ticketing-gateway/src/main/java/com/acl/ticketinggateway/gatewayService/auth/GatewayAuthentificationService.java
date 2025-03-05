package com.acl.ticketinggateway.gatewayService.auth;

import com.acl.ticketinggateway.dto.AjoutRoleUserDto;
import com.acl.ticketinggateway.dto.RoleDto;
import com.acl.ticketinggateway.request.LoginRequest;
import com.acl.ticketinggateway.request.LoginRequestDto;
import com.acl.ticketinggateway.playload.ApiResponseModel;
import com.acl.ticketinggateway.request.RegisterRequest;
import com.acl.ticketinggateway.service.auth.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GatewayAuthentificationService {

    final Logger logger = LoggerFactory.getLogger(GatewayAuthentificationService.class);

    private final AuthService authService;

    public GatewayAuthentificationService(AuthService authService) {
        this.authService = authService;
    }


    public ResponseEntity<?> authUser(LoginRequestDto loginRequestDto) {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(loginRequestDto.getPassword());
        loginRequest.setUsername(loginRequestDto.getUsername());

      return switch (loginRequestDto.getCode()) {
            case "ABXY" -> {
                logger.info("Connexion au Tpe "+ loginRequest.getUsername());
                yield authService.loginTpe(loginRequest);
            }
            case "PJOZ" -> {
                logger.info("Connexion au web "+ loginRequest.getUsername());
                yield authService.loginWeb(loginRequest);
            }
            default -> new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                    "Code not found "), HttpStatus.NOT_FOUND);
        };

    }

    public ResponseEntity<?> createUser(RegisterRequest request) {
        return authService.registerUser(request);
    }
    public ResponseEntity<?> getAllRoles(){
        return authService.getAllRoles();
    }
    public ResponseEntity<?>createRole(RoleDto roleDto){
        return authService.createRole(roleDto);
    }
    public ResponseEntity<?>desactiverRole(AjoutRoleUserDto roleDto){
        return authService.desactiverRole(roleDto);
    }


}
