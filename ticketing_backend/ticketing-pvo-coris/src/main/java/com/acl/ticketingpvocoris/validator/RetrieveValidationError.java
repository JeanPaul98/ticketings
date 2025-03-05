package com.acl.ticketingpvocoris.validator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RetrieveValidationError {

    public ResponseEntity<?> retrieveErrors(BindingResult result){
        if(result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            errors.forEach(e -> errorMap.put(e.getField(), e.getDefaultMessage()));
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

}