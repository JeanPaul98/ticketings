import {Injectable, OnDestroy} from '@angular/core';
import {BehaviorSubject, catchError, finalize, map, Observable, of, Subscription, switchMap} from 'rxjs';
import {Router} from "@angular/router";
import {AuthHttpService} from "./http/auth-http.service";
import {environment} from "../../../environments/environment";
import {getLocalStorageData, removeLocalStorageData, setLocalStorageData} from "../../../assets/ts/local-storage.utils";
import {AuthModel} from "../../models/auth.model";

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnDestroy {
// public fields
  currentUser$: Observable<any>;
  isLoading$: Observable<boolean>;
  currentUserSubject: BehaviorSubject<any>;
  isLoadingSubject: BehaviorSubject<boolean>;
  // private fields
  private unsubscribe: Subscription[] = []; // Read more: => https://brianflove.com/2016/12/11/anguar-2-unsubscribe-observables/
  private authLocalStorageToken = `${environment.appVersion}-${environment.USERDATA_KEY}`;

  constructor(
    private authHttpService: AuthHttpService,
    private router: Router,
  ) {
    this.isLoadingSubject = new BehaviorSubject<boolean>(false);
    this.currentUserSubject = new BehaviorSubject<any>(undefined);
    this.currentUser$ = this.currentUserSubject.asObservable();
    this.isLoading$ = this.isLoadingSubject.asObservable();
    const subscr = this.getUserByToken().subscribe();
    this.unsubscribe.push(subscr);
  }

  get currentUserValue(): string | undefined {
    return this.getAuthFromLocalStorage()?.username;
  }

  get currentAuthValue(): AuthModel | undefined {
    return this.getAuthFromLocalStorage();
  }

  login(request: { [p: string]: string }): Observable<any> {
    this.isLoadingSubject.next(true);
    return this.authHttpService.login(request).pipe(
      map((response) => this.setAuthFromLocalStorage(response)),
      switchMap(() => this.getUserByToken()),
      catchError((err) => {
        return of(undefined);
      }),
      finalize(() => this.isLoadingSubject.next(false))
    );
  }

  getUserByToken(): Observable<string | undefined> {
    const auth = this.getAuthFromLocalStorage();
    if (!auth || !auth.accessToken) {
      return of(undefined);
    }

    this.isLoadingSubject.next(true);
    this.currentUserSubject.next(auth.username);

    return of(auth.username);
  }

  logout() {
    removeLocalStorageData(this.authLocalStorageToken);
    this.router.navigate(['/auth/login'], {
      queryParams: {},
    }).then(r => this.isLoadingSubject.next(false));
  }

  ngOnDestroy(): void {
    this.unsubscribe.forEach((sb) => sb.unsubscribe());
  }

  private setAuthFromLocalStorage(auth: AuthModel): boolean {
    // store auth authToken/refreshToken/epires In in local storage to keep user logged in between page refreshes
    if (auth && auth.accessToken) {
      setLocalStorageData(this.authLocalStorageToken, JSON.stringify(auth));
      return true;
    }
    return false;
  }

  private getAuthFromLocalStorage(): AuthModel | undefined {
    try {
      const lsValue = getLocalStorageData(this.authLocalStorageToken);
      if (!lsValue) {
        return undefined;
      }

      return JSON.parse(lsValue);
    } catch (error) {
      return undefined;
    }
  }
}
