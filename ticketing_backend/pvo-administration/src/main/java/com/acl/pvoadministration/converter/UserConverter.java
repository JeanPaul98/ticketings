package com.acl.pvoadministration.converter;

import com.acl.pvoadministration.dto.UserDto;
import com.acl.pvoadministration.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {

    /**
     * Entity vers Dto
     * @param user
     * @return
     */

    public UserDto convertDto(User user){
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }


    /**
     * Dto vers Entity
     * @param userDto
     * @return
     */
    public User convertEntity(UserDto userDto){
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDto, User.class);
        return user;
    }

}