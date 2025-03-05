package com.acl.pvoadministration.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportManager {

    HashMap params;
    List<Map<String, ?>> fields;
    List collectionDataSource;
    File file;

    public enum ReportFormat {
        XLSX,
        DOCX,
        PDF
    }


    public ReportManager() {
        fields = new ArrayList();
        params = new HashMap();
    }

    public ReportManager(HashMap params, ArrayList fields, String filePath) throws FileNotFoundException {
        this.params = params;
        this.fields = fields;
        this.file = ResourceUtils.getFile("classpath:" + filePath);
    }

    public ReportManager(HashMap params, List collectionDataSource, String filePath) throws FileNotFoundException {
        this.params = params;
        this.collectionDataSource = collectionDataSource;
        this.file = ResourceUtils.getFile(filePath);
        System.out.println("Fichier path "+ file.getAbsolutePath());

    }

    public ReportManager(HashMap params, String filePath) throws FileNotFoundException {
        this.params = params;
        this.file = ResourceUtils.getFile("classpath:" + filePath);
    }

    public byte[] exportFromJRMapCollectionDataSourceToByte(ReportFormat reportFormat) throws JRException {
        JRMapCollectionDataSource jrMap = new JRMapCollectionDataSource(fields);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,params,jrMap);
        return getByteFrom(jasperPrint, reportFormat);
    }

    public byte[] exportFromJREmptyDataSourceToByte(ReportFormat reportFormat) throws JRException, IOException {
        System.out.println("PDF EXPORT 1");
        JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(this.collectionDataSource);

        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        System.out.println("PDF EXPORT 2");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jrBean);
        System.out.println("PDF EXPORT 3");
        return getByteFrom(jasperPrint, reportFormat);
    }

    public byte[] exportFromJRBeanCollectionDataSourceToByte(ReportFormat reportFormat) throws JRException {
        JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(this.collectionDataSource);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,params,jrBean);
        return getByteFrom(jasperPrint, reportFormat);
    }

    public byte[] getByteFrom(JasperPrint jasperPrint, ReportFormat reportFormat) throws JRException {
        JRAbstractExporter exporter = getExporter(reportFormat);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        exporter.exportReport();
        return outputStream.toByteArray();
    }

    public InputStream exportFromJRMapCollectionDataSourceToInputStream(ReportFormat reportFormat) throws JRException {
        InputStream inputStream = new ByteArrayInputStream(this.exportFromJRMapCollectionDataSourceToByte(reportFormat));
        return inputStream;
    }

   public InputStream exportFromJREmptyDataSourceToInputStream(ReportFormat reportFormat) throws JRException, IOException {
        byte[] bytes = this.exportFromJREmptyDataSourceToByte(reportFormat);
        InputStream inputStream = new ByteArrayInputStream(bytes);
        return inputStream;
    }

    public InputStream exportFromJRBeanCollectionDataSourceToInputStream(ReportFormat reportFormat) throws JRException {
        InputStream inputStream = new ByteArrayInputStream(this.exportFromJRBeanCollectionDataSourceToByte(reportFormat));
        return inputStream;
    }

    public List<Map<String, ?>> getFields() {
        return fields;
    }

    public void setFields(List<Map<String, ?>> fields) {
        this.fields = fields;
    }

    public HashMap getParams() {
        return params;
    }

    public void setParams(HashMap params) {
        this.params = params;
    }

    public List getCollectionDataSource() {
        return collectionDataSource;
    }

    public void setCollectionDataSource(List collectionDataSource) {
        this.collectionDataSource = collectionDataSource;
    }

    public File getFile() {
        return file;
    }

    public void setFile(String filePath) throws FileNotFoundException {
        this.file = ResourceUtils.getFile("classpath:" + filePath);;
    }

    private static JRAbstractExporter getExporter(ReportFormat reportFormat) {
        switch (reportFormat) {
            case DOCX: {
                return new JRDocxExporter();
            }
            case XLSX: {
                return new JRXlsxExporter();
            }
            case PDF: {
                JRPdfExporter exporter = new JRPdfExporter();
                SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                configuration.setCompressed(true);
                exporter.setConfiguration(configuration);
                return exporter;
            }
            default:
                throw new IllegalArgumentException("Unknown export type " + reportFormat);

        }
    }
}
