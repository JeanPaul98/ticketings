package com.acl.pvoadministration.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReportCorisService {

    ResponseEntity<byte[]> getReportJournalier(String dateDebut, String dateFin);
}
