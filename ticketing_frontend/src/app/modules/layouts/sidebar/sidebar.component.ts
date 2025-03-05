import {Component} from '@angular/core';
import {AuthService} from "../../../services/auth/auth.service";
import {AuthModel} from "../../../models/auth.model";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {

  constructor(private service: AuthService) {
  }

  get currentUser(): AuthModel | undefined {
    return this.service.currentAuthValue
  }
}
