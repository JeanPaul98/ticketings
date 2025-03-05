package com.acl.ticketingauth.converter;


import com.acl.ticketingauth.dto.RoleDto;
import com.acl.ticketingauth.model.Role;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleConverter {
    /**
     * Entity vers Dto
     * @param role
     * @return
     */

    public RoleDto convertDto(Role role){
        ModelMapper modelMapper = new ModelMapper();
        RoleDto roleDto = modelMapper.map(role, RoleDto.class);
        return roleDto;
    }

    /**
     * List Entities vers List Dto
     * @param roles
     * @return
     */
    public Set<RoleDto> convertDto(Set<Role> roles){
        Set<RoleDto> roleDtos = new HashSet<>();
        for(Role parc:roles){
            roleDtos.add(convertDto(parc));
        }
        return roleDtos;
    }

    /**
     * Dto vers Entity
     * @param roleDto
     * @return
     */
    public Role convertEntity(RoleDto roleDto){
        ModelMapper modelMapper = new ModelMapper();
        Role role = modelMapper.map(roleDto, Role.class);
        return role;
    }


    /**
     * List des Dto vers List Entities
     * @param roleDtos
     * @return
     */
    public Set<Role>convertEntity(Set<RoleDto> roleDtos){
        Set<Role> roles= new HashSet<>();
        for(RoleDto parc:roleDtos){
            roles.add(convertEntity(parc));
        }
        return roles;
    }
}
