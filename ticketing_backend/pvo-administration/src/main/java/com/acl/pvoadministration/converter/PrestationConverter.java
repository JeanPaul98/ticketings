package com.acl.pvoadministration.converter;

import com.acl.pvoadministration.dto.PrestationDto;
import com.acl.pvoadministration.model.Prestation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PrestationConverter {

    /**
     * Entity vers Dto
     * @param prestation
     * @return
     */

    public PrestationDto convertDto(Prestation prestation){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(prestation, PrestationDto.class);
    }


    /**
     * Dto vers Entity
     * @param prestationDto
     * @return
     */
    public Prestation convertEntity(PrestationDto prestationDto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(prestationDto, Prestation.class);
    }

}