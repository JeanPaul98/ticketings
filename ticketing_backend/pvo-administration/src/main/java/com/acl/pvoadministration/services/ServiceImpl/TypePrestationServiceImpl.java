package com.acl.pvoadministration.services.ServiceImpl;


import com.acl.pvoadministration.converter.PrestationConverter;
import com.acl.pvoadministration.converter.TypePrestationConverter;
import com.acl.pvoadministration.model.Prestation;
import com.acl.pvoadministration.model.TypePrestation;
import com.acl.pvoadministration.playload.ApiResponseModel;
import com.acl.pvoadministration.repositories.PrestationRepository;
import com.acl.pvoadministration.repositories.TypePrestationRepository;
import com.acl.pvoadministration.dto.TypePrestationDto;

import com.acl.pvoadministration.services.TypePrestationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TypePrestationServiceImpl implements TypePrestationService {

    private final Logger logger= LoggerFactory.getLogger(TypePrestationServiceImpl.class);

    private final TypePrestationRepository typePrestationRepository;

    private final TypePrestationConverter typePrestationConverter;

    private final PrestationConverter prestationConverter;

    private final PrestationRepository prestationRepository;

    /**
     * Création du type de prestation
     * @param request
     * @return ResponseEntity<?>
     */
    @Override
    public ResponseEntity<?> createType(TypePrestationDto request) {
        logger.info("prestation {}",request);
//        Prestation prestation = prestationConverter.
//                convertEntity(typePrestationDto.getPrestationDto());
        TypePrestation type = typePrestationConverter.convertEntity(request);
        Optional<Prestation> prestation1 = prestationRepository.
                findByDesignation(request.getPrestation());
        if(prestation1.isPresent()) {
            type.setPrestation(prestation1.get());
            type.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            Optional<TypePrestation> typePrestation = typePrestationRepository
                    .findTypePrestationByDesignation(request.getDesignation());
            if(typePrestation.isPresent()) {
                return new ResponseEntity<>(new ApiResponseModel(HttpStatus.CONFLICT.name(),
                        "La prestation existe déja "),HttpStatus.OK);
            } else {
                typePrestationRepository.save(type);
                return new ResponseEntity<>(new ApiResponseModel(HttpStatus.CREATED.name(),
                        "Opération réussie ",request),HttpStatus.OK);
            }
        }else {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                    "Opération échoué "),HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<?> getAllTypePrestations() {
        return new ResponseEntity<>(new ApiResponseModel(HttpStatus.OK.name(),
                "Opération réussie ",typePrestationRepository.findAll()),HttpStatus.OK);
    }


}
