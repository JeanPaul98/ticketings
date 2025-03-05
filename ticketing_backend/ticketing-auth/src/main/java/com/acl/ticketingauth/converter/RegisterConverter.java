package com.acl.ticketingauth.converter;

import com.acl.ticketingauth.dto.RegisterDto;
import com.acl.ticketingauth.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RegisterConverter {

    /**
     * Entity vers Dto
     * @param user
     * @return
     */

    public RegisterDto convertDto(User user){
        ModelMapper modelMapper = new ModelMapper();
        RegisterDto registerDto = modelMapper.map(user, RegisterDto.class);
        return registerDto;
    }


    /**
     * Dto vers Entity
     * @param registerDto
     * @return
     */
    public User convertEntity(RegisterDto registerDto){
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(registerDto, User.class);
        return user;
    }
}
