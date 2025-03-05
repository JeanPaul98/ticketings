import {Injectable} from "@angular/core";
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import {catchError, Observable, of} from "rxjs";
import {getAuthLocalDataStorage} from "../../assets/ts/local-storage.utils";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: "root"
})
export class AppHttpInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log(`Sending request to ${req.url}`);
    if (
      req.url.endsWith("/ticketing-gateway/auth/login")
    ) {
      return next.handle(req);
    } else {
      const authToken = getAuthLocalDataStorage(`${environment.appVersion}-${environment.USERDATA_KEY}`);
      const authReq = req.clone({
        headers: req.headers.set("Authorization", `Bearer ${authToken?.accessToken}`),
      });
      return next.handle(authReq).pipe(
        catchError((error) => {
          console.log(error.status);
          if (error.status === 401) {
            // this.authService.getRefreshToken().pipe(
            //     switchMap(() => next.handle(req))
            // ).subscribe();
            return next.handle(req);
          }
          return of(error);
        })
      );
    }
  }
}
