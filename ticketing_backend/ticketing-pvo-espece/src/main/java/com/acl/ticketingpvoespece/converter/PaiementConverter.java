package com.acl.ticketingpvoespece.converter;

import com.acl.ticketingpvoespece.model.Paiement;
import com.acl.ticketingpvoespece.dto.PaiementDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PaiementConverter {

    /**
     * Entity vers Dto
     * @param paiement
     * @return
     */

    public PaiementDto convertDto(Paiement paiement){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(paiement, PaiementDto.class);
    }


    /**
     * Dto vers Entity
     * @param paiementDto
     * @return
     */
    public Paiement convertEntity(PaiementDto paiementDto){
        ModelMapper modelMapper = new ModelMapper();
        Paiement paiement = modelMapper.map(paiementDto, Paiement.class);
        return paiement;
    }
}
