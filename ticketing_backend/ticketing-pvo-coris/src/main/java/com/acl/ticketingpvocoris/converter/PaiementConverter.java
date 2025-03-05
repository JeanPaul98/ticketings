package com.acl.ticketingpvocoris.converter;

import com.acl.ticketingpvocoris.dto.PaiementDto;
import com.acl.ticketingpvocoris.model.Paiement;
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
        PaiementDto paiementDto = modelMapper.map(paiement, PaiementDto.class);
        return paiementDto;
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
