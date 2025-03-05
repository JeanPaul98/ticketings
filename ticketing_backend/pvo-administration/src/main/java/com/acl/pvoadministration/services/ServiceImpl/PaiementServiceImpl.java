package com.acl.pvoadministration.services.ServiceImpl;


import com.acl.pvoadministration.dto.PaiementDto;
import com.acl.pvoadministration.model.Paiement;
import com.acl.pvoadministration.playload.ApiResponseModel;
import com.acl.pvoadministration.repositories.PaiementRepository;
import com.acl.pvoadministration.services.PaiementService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PaiementServiceImpl implements PaiementService {

    private final Logger logger = LoggerFactory.getLogger(PaiementServiceImpl.class);

    private final PaiementRepository paiementRepository;


    @Override
    public ResponseEntity<?> listePaiement() {
        List<Paiement> response = paiementRepository.findAllTransaction();
        logger.info("Liste des transactions effectuées {}:",response);
        return new ResponseEntity<>(new ApiResponseModel(HttpStatus.OK.name(), "Liste des transactions effectuées ", response), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllTransactionDay() {
        List<Paiement> response = paiementRepository.findAllTransactionDay();
        logger.info("Liste des transactions effectuées {}:",response);
        return new ResponseEntity<>(new ApiResponseModel(HttpStatus.OK.name(), "Liste des transactions effectuées dans la journée ", response), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> search(String searchValue) {
        List<Paiement> response = paiementRepository.findByAllColumns(searchValue);
        logger.info("Liste des transactions effectuées {}:",response);
        return new ResponseEntity<>(new ApiResponseModel(HttpStatus.OK.name(), "Liste des transactions effectuées", response), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> searchByDate(Date dateDebut, Date dateFin) {
        List<Paiement> response = paiementRepository.searchByDate(dateDebut, dateFin);
        logger.info("Liste des transactions effectuées {}:",response);
        return new ResponseEntity<>(new ApiResponseModel(HttpStatus.OK.name(), "Liste des transactions effectuées", response), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> searchAndDate(Date dateDebut, Date dateFin, String searchValue) {
        List<Paiement> response = paiementRepository.searchAndDay(dateDebut, dateFin, searchValue);
        logger.info("Liste des transactions effectuées {}:",response);
        return new ResponseEntity<>(new ApiResponseModel(HttpStatus.OK.name(), "Liste des transactions effectuées", response), HttpStatus.OK);
    }

}
