package com.acl.pvoadministration.converter;

import com.acl.pvoadministration.dto.TypePrestationDto;
import com.acl.pvoadministration.model.TypePrestation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TypePrestationConverter {

    /**
     * Entity vers Dto
     * @param
     * @return
     */

    public TypePrestationDto convertDto(TypePrestation typePrestation){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(typePrestation, TypePrestationDto.class);
    }


    /**
     * Dto vers Entity
     * @param typePrestationDto
     * @return
     */
    public TypePrestation convertEntity(TypePrestationDto typePrestationDto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(typePrestationDto, TypePrestation.class);
    }

}