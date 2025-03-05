package com.acl.ticketingnotification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Zansouye
 */
@Getter
@Setter
@Schema(description = "Le format d'enregistrement dans la bdd de notification de paiement du PAL")
public class TicketingRecettesDto {

    @NotNull
    @NotBlank
    private String codeTicket;

    @NotNull
    @NotBlank
    private String reference;

    @NotNull
    @NotBlank
    private String transactionID;

    private String telephone;

    @NotNull
    @NotBlank
    private String operateur;

    @NotNull
    @NotBlank
    private double montant;


}
