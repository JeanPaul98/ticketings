import {Injectable} from '@angular/core';
import {TransactionHttpService} from "./transaction-http.service";
import {catchError, map, Observable, of} from "rxjs";
import {TransactionModel} from "../../../../../models/transaction.model";
import {ValidateModel} from "../../../../../models/validate.model";
import {getDownloadPDF} from "../../../../../../assets/ts/main";
import {formatDate} from "@angular/common";
import {SwalService} from "../../../../../services/swal.service";

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private httpService: TransactionHttpService, private swalService: SwalService) {
  }

  listTransaction(): Observable<TransactionModel[] | undefined> {
    return this.httpService.list().pipe(
      map((response) => response.result),
      catchError((err) => {
        console.error(err);
        return of(undefined);
      }),
    )
  }

  searchTransaction(searchValue: string): Observable<TransactionModel[] | undefined> {
    return this.httpService.search(searchValue).pipe(
      map((response) => response.result),
      catchError((err) => {
        console.error(err);
        return of(undefined);
      }),
    )
  }

  listTransactionByDate(dateDebut: string, dateFin: string): Observable<TransactionModel[] | undefined> {
    return this.httpService.listByDate(dateDebut, dateFin).pipe(
      map((response) => response.result),
      catchError((err) => {
        console.error(err);
        return of(undefined);
      }),
    )
  }

  searchTransactionByDate(searchValue: string, dateDebut: string, dateFin: string): Observable<TransactionModel[] | undefined> {
    return this.httpService.searchByDate(searchValue, dateDebut, dateFin).pipe(
      map((response) => response.result),
      catchError((err) => {
        console.error(err);
        return of(undefined);
      }),
    )
  }

  reportTransaction(): Observable<TransactionModel[] | undefined> {
    return this.httpService.report().pipe(
      map((response) => response.result),
      catchError((err) => {
        console.error(err);
        return of(undefined);
      }),
    )
  }

  checkTicket(codeTicket: string): Observable<ValidateModel | undefined> {
    let request = Object.assign({}, {codeTicket: codeTicket});
    return this.httpService.checkTicket(request).pipe(
      map((response) => response.result),
      catchError((err) => {
        console.error(err);
        return of(undefined);
      })
    )
  }

  reportTransactionByDate(request: { [p: string]: string }): Observable<any> {
    const startDate = formatDate(request['startDate'], 'yyyy-MM-dd', 'en');
    const endDate = formatDate(request['endDate'], 'yyyy-MM-dd', 'en');

    if (new Date(startDate) > new Date(endDate)) {
      this.swalService.toastError('La date de début est supérieure à la date de fin.');
      return of(undefined);
    }

    return this.httpService.reportByDate(startDate, endDate).pipe(
      map((response: Blob) => getDownloadPDF(response, startDate, endDate)),
      catchError((err) => {
        this.swalService.toastError(err.message);
        return of(undefined);
      })
    )
  }


}
