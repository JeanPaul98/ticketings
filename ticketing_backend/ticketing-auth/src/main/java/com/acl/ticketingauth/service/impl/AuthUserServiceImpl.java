package com.acl.ticketingauth.service.impl;

import com.acl.ticketingauth.converter.RegisterConverter;
import com.acl.ticketingauth.converter.RoleConverter;
import com.acl.ticketingauth.dto.AjoutRoleUserDto;
import com.acl.ticketingauth.dto.RegisterDto;
import com.acl.ticketingauth.model.Role;
import com.acl.ticketingauth.model.User;
import com.acl.ticketingauth.playload.ApiResponseModel;
import com.acl.ticketingauth.repositories.RoleRepository;
import com.acl.ticketingauth.repositories.UserRepository;
import com.acl.ticketingauth.service.AuthUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final Logger logger = LoggerFactory.getLogger(AuthUserServiceImpl.class);

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public AuthUserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
                               RoleRepository roleRepository){
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
    }

    /**
     * @author Zansouyé
     * @param registerDto
     * @return
     */
    @Override
    public ResponseEntity<?> createUser(RegisterDto registerDto) {

       if(registerDto.getRoleDtoSet().isEmpty()){
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_ACCEPTABLE.name(),
                    "Role n'existe pas"), HttpStatus.OK);
        }

        logger.info("createUser ");
        RegisterConverter registerConverter= new RegisterConverter();
        RoleConverter roleConverter= new RoleConverter();
        User user= registerConverter.convertEntity(registerDto);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setActif(true);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roleSet= new HashSet<>();
        for(AjoutRoleUserDto r: registerDto.getRoleDtoSet()){
            logger.info("chaque role "+r.toString());

            Optional<Role> roleOptional= roleRepository.findByCode(r.getCode());
            if(roleOptional.isPresent()){
                roleSet.add(roleOptional.get());
            }else{
                return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NO_CONTENT.name(),
                        "Role n'existe pas"), HttpStatus.OK);
            }

        }
        logger.info("tous les roles "+roleSet.toString());
        user.setRoles(roleSet);
        User saveUser= userRepository.save(user);
        return  new ResponseEntity<>(new ApiResponseModel(HttpStatus.CREATED.name(),
                "Opération effectuée avec succès", saveUser), HttpStatus.OK);


       /* User user = new User();
        Role role = roleRepository.findByCode("CAISSIER").orElseThrow();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPrenom(request.getFirstname());
        user.setNom(request.getLastname());
        user.setTelephone(request.getTelephone());
        user.setRoles(Set.of(role));
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponseModel(HttpStatus.OK.name(), "User created successfully"));*/
    }
}
