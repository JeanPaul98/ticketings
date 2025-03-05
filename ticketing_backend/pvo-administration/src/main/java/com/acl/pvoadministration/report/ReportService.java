package com.acl.pvoadministration.report;

import com.acl.pvoadministration.model.Paiement;
import com.acl.pvoadministration.model.ReportCorisModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportService {


    final DecimalFormat decimalFormat = new DecimalFormat("###,###.###");



    SimpleDateFormat sf = new SimpleDateFormat("EEEE dd MMMM yyyy");
    SimpleDateFormat sfDate = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sfDateHeure = new SimpleDateFormat("dd/MM/yyyy HH:mm");



    public InputStream reporTingCoris(List<ReportCorisModel> factureCorises, List<Paiement> paiements, Date debut, Date fin, String format) throws IOException, JRException {

        List<Map<String, ?>> fields = new ArrayList();
        JRBeanCollectionDataSource jrDetailBeanCollection = new JRBeanCollectionDataSource(factureCorises);
        String filePath = "docs/corisReporting.jrxml";
        double montantTotal = paiements.stream().mapToDouble(Paiement::getMontant).sum();

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("dateDebut",sfDate.format(debut));
        params.put("dateFin", sfDate.format(fin));
        params.put("logo","docs/images/pal.png");
        params.put("montantTotal",decimalFormat.format(montantTotal));

        ReportManager reportManager = new ReportManager(params, factureCorises,filePath);

        InputStream inputStream = null;
        switch (format.toUpperCase()) {
            case "PDF" :
                System.out.println("PDF");
                inputStream = reportManager.exportFromJREmptyDataSourceToInputStream(ReportManager.ReportFormat.PDF);
                break;
            case "DOCX" :
                inputStream = reportManager.exportFromJREmptyDataSourceToInputStream(ReportManager.ReportFormat.DOCX);
                break;
        }

        return  inputStream;
    }


}
