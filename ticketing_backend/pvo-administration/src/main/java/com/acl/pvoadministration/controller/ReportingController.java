package com.acl.pvoadministration.controller;

import com.acl.pvoadministration.playload.ApiResponseModel;
import com.acl.pvoadministration.services.ReportCorisService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/admin/report")
public class ReportingController {

private final ReportCorisService reportCorisService;

    public ReportingController(ReportCorisService reportCorisService) {
        this.reportCorisService = reportCorisService;
    }

    @CrossOrigin
    @GetMapping("coris")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "liste des paiements par période", response = ApiResponseModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request: The name field is required"),
            @ApiResponse(code = 200, message = "OK: the operation is successfully ")})
    public ResponseEntity<?> imprimeCoris(  @RequestParam("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") String dateDebut,
                                            @RequestParam("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFin) {


        return reportCorisService.getReportJournalier(dateDebut , dateFin );
    }


}
