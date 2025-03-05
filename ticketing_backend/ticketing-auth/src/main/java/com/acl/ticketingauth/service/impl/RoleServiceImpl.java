package com.acl.ticketingauth.service.impl;

import com.acl.ticketingauth.converter.RoleConverter;
import com.acl.ticketingauth.dto.AjoutRoleUserDto;
import com.acl.ticketingauth.dto.RoleDto;
import com.acl.ticketingauth.model.Role;
import com.acl.ticketingauth.playload.ApiResponseModel;
import com.acl.ticketingauth.repositories.RoleRepository;
import com.acl.ticketingauth.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * @author Zansouyé
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }
    @Override
    public ResponseEntity<?> createRole(RoleDto roleDto) {

        RoleConverter roleConverter= new RoleConverter();

        Optional<Role>optionalRole=roleRepository.findByCode(roleDto.getCode());

        if(optionalRole.isPresent()){
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_ACCEPTABLE.name(),
                    "Role existe déjà"), HttpStatus.OK);
        }else{
            Role role= roleConverter.convertEntity(roleDto);
            role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            Role roleSave= roleRepository.save(role);
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.CREATED.name(),
                    "Operation effectuée avec succès",roleSave), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<?> desactiverRole(AjoutRoleUserDto roleDto) {

        Optional<Role>optionalRole=roleRepository.findByCode(roleDto.getCode());

        if(optionalRole.isPresent()){

            optionalRole.get().setActif(false);
            optionalRole.get().setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Role roleSave= roleRepository.save(optionalRole.get());
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.OK.name(),
                    "Operation effectuée avec succès",roleSave), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_ACCEPTABLE.name(),
                    "Role introuvable"), HttpStatus.OK);
        }


    }

    @Override
    public ResponseEntity<?> getAllRolesActifs() {

        return new ResponseEntity<>(new ApiResponseModel(HttpStatus.OK.name(),
                "Operation effectuée avec succès",roleRepository.listRolesActif()), HttpStatus.OK);
    }
}
