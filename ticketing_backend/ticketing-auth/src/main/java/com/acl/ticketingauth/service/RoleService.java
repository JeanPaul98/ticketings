package com.acl.ticketingauth.service;

import com.acl.ticketingauth.dto.AjoutRoleUserDto;
import com.acl.ticketingauth.dto.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Zansouyé
 */
@Service
public interface RoleService {

    ResponseEntity<?>createRole(RoleDto roleDto);

    ResponseEntity<?>desactiverRole(AjoutRoleUserDto roleDto);

    ResponseEntity<?>getAllRolesActifs();
}
