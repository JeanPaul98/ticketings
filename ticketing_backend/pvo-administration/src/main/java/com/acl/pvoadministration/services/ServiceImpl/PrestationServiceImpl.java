package com.acl.pvoadministration.services.ServiceImpl;

import com.acl.pvoadministration.converter.PrestationConverter;
import com.acl.pvoadministration.model.Prestation;
import com.acl.pvoadministration.playload.ApiResponseModel;
import com.acl.pvoadministration.repositories.PrestationRepository;
import com.acl.pvoadministration.dto.PrestationDto;
import com.acl.pvoadministration.services.PrestationService;
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
public class PrestationServiceImpl implements PrestationService {

    private final Logger logger= LoggerFactory.getLogger(PrestationServiceImpl.class);

    private final PrestationRepository prestationRepository;

    private final PrestationConverter prestationConverter;

    @Override
    public ResponseEntity<?> save(PrestationDto prestationDto) {
        logger.info("prestation {}",prestationDto);
        Prestation prestation = prestationConverter.convertEntity(prestationDto);
        Optional<Prestation> prestation1 = prestationRepository.
                findByDesignation(prestationDto.getDesignation());
        if(prestation1.isPresent()) {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.CONFLICT.name(),
                    "La prestation existe déja "),HttpStatus.OK);
        }else {
            prestation.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            prestation.setDesignation(prestationDto.getDesignation().toUpperCase());
            prestationRepository.save(prestation);
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.CREATED.name(),
                    "Opération réussi ",prestationDto),HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getPrestationDetail(String designation) {
        Optional<Prestation> prestation = prestationRepository.findByDesignation(designation);
        if(prestation.isPresent()) {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.CREATED.name(),
                    "Opération réussie ",prestation),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                    "Prestation not found "),HttpStatus.OK);
        }

    }
    @Override
    public ResponseEntity<?> getAllPrestations() {
        return new ResponseEntity<>(new ApiResponseModel(HttpStatus.CREATED.name(),
                "Opération réussie ",prestationRepository.findAll()),HttpStatus.OK);
    }

}
