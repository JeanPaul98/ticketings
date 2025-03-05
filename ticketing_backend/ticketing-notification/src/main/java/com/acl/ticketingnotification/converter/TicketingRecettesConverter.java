package com.acl.ticketingnotification.converter;

import com.acl.ticketingnotification.dto.TicketingRecettesDto;
import com.acl.ticketingnotification.model.TicketingRecettes;
import org.modelmapper.ModelMapper;

/**
 * @author Zansouye
 */
public class TicketingRecettesConverter {


    /**
     * Entity vers Dto
     * @param ticketingRecettes
     * @return
     */

    public TicketingRecettesDto convertDto(TicketingRecettes ticketingRecettes){
        ModelMapper modelMapper = new ModelMapper();
        TicketingRecettesDto ticketingRecettesDto = modelMapper.map(ticketingRecettes, TicketingRecettesDto.class);
        return ticketingRecettesDto;
    }


    /**
     * Dto vers Entity
     * @param ticketingRecettesDto
     * @return
     */
    public TicketingRecettes convertEntity(TicketingRecettesDto ticketingRecettesDto){
        ModelMapper modelMapper = new ModelMapper();
        TicketingRecettes ticketingRecettes= modelMapper.map(ticketingRecettesDto, TicketingRecettes.class);
        return ticketingRecettes;
    }
}
