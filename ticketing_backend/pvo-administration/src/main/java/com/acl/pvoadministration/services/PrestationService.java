package com.acl.pvoadministration.services;


import com.acl.pvoadministration.dto.PrestationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PrestationService {

        ResponseEntity<?> save(PrestationDto prestationDto);
        ResponseEntity<?>  getPrestationDetail(String designation);
        ResponseEntity<?>  getAllPrestations();
}
