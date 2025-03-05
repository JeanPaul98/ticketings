import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {AuthService} from "../../../services/auth/auth.service";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  constructor(private service: AuthService) {
  }

  logout(){
    this.service.logout()
  }
}
