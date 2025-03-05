package com.acl.ticketingauth.service.impl;


import com.acl.ticketingauth.converter.RegisterConverter;
import com.acl.ticketingauth.converter.RoleConverter;
import com.acl.ticketingauth.model.Role;
import com.acl.ticketingauth.model.User;
import com.acl.ticketingauth.playload.ApiResponseModel;
import com.acl.ticketingauth.playload.JwtAuthenticationResponse;
import com.acl.ticketingauth.requests.LoginRequest;
import com.acl.ticketingauth.dto.RegisterDto;
import com.acl.ticketingauth.repositories.RoleRepository;
import com.acl.ticketingauth.repositories.UserRepository;
import com.acl.ticketingauth.security.JwtServices;
import com.acl.ticketingauth.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final JwtServices jwtServices;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(JwtServices jwtServices, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.jwtServices = jwtServices;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Connexion de l'utilisateur
     *
     * @param loginRequest
     * @return
     */
    @Override
    public ResponseEntity<?> connexionTpe(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        if (userOptional.isPresent()) {
            logger.info("User is present {} ", userOptional.get().getUsername());
            return checkUserDetailsTpe(loginRequest, userOptional);
        } else {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                    "User not found"), HttpStatus.OK);
        }
    }

    /**
     * Vérifie si l'utilisateur est présent
     *
     * @param loginRequest
     * @param userOptional
     * @return
     */
    private ResponseEntity<?> checkUserDetailsTpe(LoginRequest loginRequest, Optional<User> userOptional) {
        boolean matches = passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword());
        logger.info("User password Match : " + matches);
        if (matches) {
            return checkRoleTpe(userOptional);
        } else {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                    "Password Incorrect"), HttpStatus.OK);
        }
    }

    /**
     * Controle le role de l'utilisateur
     *
     * @param userOptional
     * @return
     */
    private ResponseEntity<?> checkRoleTpe(Optional<User> userOptional) {
        for (Role i : userOptional.get().getRoles()) {
            if (i.getCode().equalsIgnoreCase("CAISSIER")) {
                logger.info("login Caissier " + i.getCode());
                String jwt = jwtServices.generateToken(userOptional.get());
                return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, "Bearer",
                        userOptional.get().getUsername(), userOptional.get().getPrenom(), userOptional.get().getNom(), userOptional.get().getTelephone()));
            } else {
                logger.info("login Admin");
                return new ResponseEntity<>(new ApiResponseModel(HttpStatus.FORBIDDEN.name(),
                        "Role is not Incorrect"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                "Role not found"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> connexionWeb(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        if (userOptional.isPresent()) {
            logger.info("User is present {} ", userOptional.get().getUsername());
            return checkUserDetailsWeb(loginRequest, userOptional);
        } else {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                    "User not found"), HttpStatus.OK);
        }

    }



    /**
     * Vérifie si l'utilisateur est présent
     *
     * @param loginRequest
     * @param userOptional
     * @return
     */
    private ResponseEntity<?> checkUserDetailsWeb(LoginRequest loginRequest, Optional<User> userOptional) {
        boolean matches = passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword());
        logger.info("User web password Match : " + matches);
        if (matches) {
            return checkRoleWeb(userOptional);
        } else {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(), "Password Incorrect"), HttpStatus.OK);
        }

    }

    /**
     * Controle le role de l'utilisateur
     *
     * @param userOptional
     * @return
     */
    private ResponseEntity<?> checkRoleWeb(Optional<User> userOptional) {
        for (Role i : userOptional.get().getRoles()) {
            if (i.getCode().equalsIgnoreCase("ADMIN")) {
                logger.info("login Admin  role" + i.getCode());
                String jwt = jwtServices.generateToken(userOptional.get());
                logger.info("login Admin  jwt" + jwt);
                return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, "Bearer", userOptional.get().getUsername(), userOptional.get().getPrenom(), userOptional.get().getNom(), userOptional.get().getTelephone()));
            } else {
                logger.info("login Caissier");
                return new ResponseEntity<>(new ApiResponseModel(HttpStatus.FORBIDDEN.name(),
                        "Role is not Incorrect"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                "Users not  found check Role"), HttpStatus.OK);
    }
}
