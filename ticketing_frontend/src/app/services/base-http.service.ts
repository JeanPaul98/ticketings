import {Injectable} from "@angular/core";
import { HttpHeaders } from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: "root"
})
export class BaseHttpService {

    protected API_USERS_URL = environment.apiUrl;

    protected httpHeader(): HttpHeaders {
        return new HttpHeaders()
            .set("accept", "application/json; charset=utf-8")
            .set("Access-Control-Allow-Origin", "*");
    }
}
