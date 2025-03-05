import {Injectable} from '@angular/core';
import {BaseHttpService} from "../../../../../services/base-http.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {HttpResponseModel} from "../../../../../models/http-response.model";
import {TransactionModel} from "../../../../../models/transaction.model";
import {ValidateModel} from "../../../../../models/validate.model";
import {formatDate} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class TransactionHttpService extends BaseHttpService {

  constructor(private http: HttpClient) {
    super();
  }

  list(): Observable<HttpResponseModel<TransactionModel[]>> {
    return this.http.get<HttpResponseModel<TransactionModel[]>>(`${this.API_USERS_URL}/api/payments/list`, {
      headers: this.httpHeader()
    });
  }

  search(searchValue: string): Observable<HttpResponseModel<TransactionModel[]>> {
    return this.http.get<HttpResponseModel<TransactionModel[]>>(`${this.API_USERS_URL}/api/payments/search?searchValue=${searchValue}`, {
      headers: this.httpHeader()
    });
  }

  listByDate(dateDebut: string, dateFin: string): Observable<HttpResponseModel<TransactionModel[]>> {
    return this.http.get<HttpResponseModel<TransactionModel[]>>(`${this.API_USERS_URL}/api/payments/date/search?dateDebut=${dateDebut}&dateFin=${dateFin}`, {
      headers: this.httpHeader()
    });
  }

  searchByDate(searchValue: string, dateDebut: string, dateFin: string): Observable<HttpResponseModel<TransactionModel[]>> {
    return this.http.get<HttpResponseModel<TransactionModel[]>>(`${this.API_USERS_URL}/api/payments/date/search?dateDebut=${dateDebut}&dateFin=${dateFin}`, {
      headers: this.httpHeader()
    });
  }

  report(): Observable<HttpResponseModel<TransactionModel[]>> {
    return this.http.get<HttpResponseModel<TransactionModel[]>>(`${this.API_USERS_URL}/api/payments/list/day`, {
      headers: this.httpHeader()
    });
  }

  checkTicket(request: {}): Observable<HttpResponseModel<ValidateModel>> {
    return this.http.post<HttpResponseModel<ValidateModel>>(`${this.API_USERS_URL}/api/admin/ticket/code`, request, {
      headers: this.httpHeader()
    });
  }

  reportByDate(startDate: string, endDate: string): Observable<Blob> {
    return this.http.get<Blob>(`${this.API_USERS_URL}/api/reporting/coris?dateDebut=${startDate}&dateFin=${endDate}`, {
      headers: this.httpHeader(),
      responseType: 'blob' as 'json'
    });
  }
}
