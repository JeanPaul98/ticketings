package com.acl.pvoadministration.repositories;



import com.acl.pvoadministration.model.Paiement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    Paiement findByCodeTicket(String codeTicket);

    @Query(value = "select p.CODE_TICKET, p.MONTANT,p.TYPE_PAIEMENT ,p.REFERENCE," +
            "p.TRANSACTION_ID,p.TELEPHONE,p.OPERATEUR,p.CREATED_AT," +
            "p.DATE_EXPIRATION from sipedba.ticketing_Paiement p ORDER BY p.CREATED_AT DESC", nativeQuery = true)
    Page<Paiement>  findAllTransactionPage(Pageable pageable);

    @Query(value = "select * from sipedba.ticketing_Paiement ORDER BY CREATED_AT DESC", nativeQuery = true)
    List<Paiement>  findAllTransaction();


    @Query(value = "select * from sipedba.ticketing_Paiement ORDER BY CREATED_AT DESC", nativeQuery = true)
    List<Paiement>  findTransaction();

    @Query(value = "select * from sipedba.ticketing_Paiement WHERE TRUNC(CREATED_AT) = TRUNC(SYSDATE) ORDER BY CREATED_AT DESC", nativeQuery = true)
    List<Paiement>  findAllTransactionDay();

    @Query(value = "select * from sipedba.ticketing_Paiement " +
            "WHERE CODE_TICKET IS NOT NULL " +
            "and TRANSACTION_ID IS NOT NULL " +
            "and REFERENCE IS NOT NULL " +
            "and (TELEPHONE = ?1 " +
            "or CODE_TICKET = ?1 " +
            "or REFERENCE = ?1 " +
            "or TRANSACTION_ID = ?1 " +
            "or OPERATEUR = ?1)" +
            "ORDER BY CREATED_AT DESC", nativeQuery = true)
    List<Paiement> findByAllColumns(String searchValue);


    @Query(value = "select * from sipedba.ticketing_Paiement " +
            "WHERE CODE_TICKET IS NOT NULL " +
            "and TRANSACTION_ID IS NOT NULL " +
            "and REFERENCE IS NOT NULL " +
            "and CREATED_AT BETWEEN ?1 AND ?2 " +
            "and (TELEPHONE = ?3 " +
            "or CODE_TICKET = ?3 " +
            "or REFERENCE = ?3 " +
            "or TRANSACTION_ID = ?3 " +
            "or OPERATEUR = ?3)" +
            "ORDER BY CREATED_AT DESC", nativeQuery = true)
    List<Paiement> searchAndDay(Date startDate,Date endDate,String searchValue);


    @Query(value = "select * from sipedba.ticketing_Paiement " +
            " WHERE CODE_TICKET IS NOT NULL " +
            "and TRANSACTION_ID IS NOT NULL " +
            "and REFERENCE IS NOT NULL " +
            "and CREATED_AT BETWEEN ?1 AND ?2 " +
            "ORDER BY CREATED_AT DESC", nativeQuery = true)
    List<Paiement> searchByDate(Date startDate,Date endDate);


}