import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {first, Observable, Subscription} from "rxjs";
import {Router} from "@angular/router";
import {AuthService} from "../../../../services/auth/auth.service";
import {getResult} from "../../../../../assets/ts/main";
import {AsyncPipe, NgTemplateOutlet} from "@angular/common";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    AsyncPipe,
    NgTemplateOutlet
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit, OnDestroy {

  hasError: boolean = false;
  isLoading$: Observable<boolean>;
  loginForm: FormGroup = new FormGroup({});

  // private fields
  private unsubscribe: Subscription[] = []; // Read more: => https://brianflove.com/2016/12/11/anguar-2-unsubscribe-observables/


  constructor(
    private router: Router,
    private fb: FormBuilder,
    private authService: AuthService,
  ) {
    this.isLoading$ = this.authService.isLoading$;
    // redirect to home if already logged in
    console.log(this.authService.currentUserValue)
    if (this.authService.currentUserValue) {
      this.router.navigateByUrl("/");
    }
  }

// convenience getter for easy access to form fields
  get f() {
    return this.loginForm.controls;
  }

  ngOnDestroy(): void {
    this.unsubscribe.forEach((sb) => sb.unsubscribe());
  }

  ngOnInit(): void {
    this.initForm();
  }

  save(): void {
    this.hasError = false;
    const data = getResult(this.f);
    const loginSubscr = this.authService.login(data)
      .pipe(first()).subscribe((user: string | undefined) => {
        if (user) {
          this.router.navigateByUrl("/");
        } else {
          this.hasError = true;
        }
      });
    this.unsubscribe.push(loginSubscr);
  }

  private initForm(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.compose([Validators.required])],
      code: ['PJOZ']
    })
  }
}
//accessToken
