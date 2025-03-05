import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import { HttpClient } from "@angular/common/http";
import {BaseHttpService} from "../../base-http.service";
import {AuthModel} from "../../../models/auth.model";

@Injectable({
  providedIn: 'root'
})
export class AuthHttpService extends BaseHttpService {

  constructor(private http: HttpClient) {
    super();
  }

  login(request: { [p: string]: string }): Observable<AuthModel> {
    return this.http.post<AuthModel>(`${this.API_USERS_URL}/auth/login`, request, {
      headers: this.httpHeader()
    });
  }
}
