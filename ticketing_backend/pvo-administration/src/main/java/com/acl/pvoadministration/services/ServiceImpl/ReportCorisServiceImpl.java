package com.acl.pvoadministration.services.ServiceImpl;

import com.acl.pvoadministration.model.Paiement;
import com.acl.pvoadministration.model.ReportCorisModel;
import com.acl.pvoadministration.report.ReportService;
import com.acl.pvoadministration.repositories.PaiementRepository;
import com.acl.pvoadministration.services.ReportCorisService;
import fr.opensagres.xdocreport.core.io.IOUtils;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ReportCorisServiceImpl implements ReportCorisService {

    final DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
    private final Logger logger= LoggerFactory.getLogger(ReportCorisServiceImpl.class);


    private final PaiementRepository paiementRepository;

   private final ReportService reportService;

    public ReportCorisServiceImpl(PaiementRepository paiementRepository, ReportService reportService) {
        this.paiementRepository = paiementRepository;
        this.reportService = reportService;
    }

    @Override
    public ResponseEntity<byte[]> getReportJournalier(String dateDebut , String dateFin) {
        HttpHeaders headers = new HttpHeaders();
        InputStream in;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = dateFormat.parse(dateDebut);
            Date endDate = dateFormat.parse(dateFin);
            logger.info("Date debut {}, Date fin {}", startDate, endDate);

            List<Paiement> paiements = paiementRepository.searchByDate(startDate, endDate);
            List<ReportCorisModel> reportCorisModels = new ArrayList<>();
            for (Paiement paiement: paiements) {
                ReportCorisModel reportCorisModel = new ReportCorisModel();
                reportCorisModel.setCodeTicket(paiement.getCodeTicket());
                reportCorisModel.setMontant(decimalFormat.format(paiement.getMontant()));
                reportCorisModel.setReference(paiement.getReference());
                reportCorisModel.setTelephone(paiement.getTelephone());
                reportCorisModel.setDatePaiement(dateFormat.format(paiement.getCreatedAt()));
                reportCorisModels.add(reportCorisModel);
            }

            in = this.reportService.reporTingCoris(reportCorisModels,paiements,startDate, endDate, "PDF");
            byte[] media = IOUtils.toByteArray(in);
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            headers.setContentType(MediaType.APPLICATION_PDF);
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);

            return responseEntity;

        } catch (ParseException | JRException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
