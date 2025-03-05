import {CanActivateFn} from '@angular/router';
import {Injectable} from "@angular/core";
import {AuthService} from "./auth.service";

@Injectable({providedIn: 'root'})
export class AuthGuard {

  constructor(
    private authService: AuthService
  ) {
  }

  canActivate: CanActivateFn = (route, state) => {
    // const authService = inject(AuthService);
    const currentUser = this.authService.currentUserValue;

    if (currentUser) {
      // logged in so return true
      return true;
    }

    this.authService.logout()
    return false;
  };
}

