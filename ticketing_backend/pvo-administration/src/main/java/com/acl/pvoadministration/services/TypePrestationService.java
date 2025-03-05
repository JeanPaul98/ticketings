package com.acl.pvoadministration.services;

import com.acl.pvoadministration.dto.TypePrestationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface TypePrestationService {
   ResponseEntity<?> createType(TypePrestationDto typePrestationDto);
   ResponseEntity<?> getAllTypePrestations();
}
