import {AfterViewInit, Component} from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {FooterComponent} from "../layouts/footer/footer.component";
import {SidebarComponent} from "../layouts/sidebar/sidebar.component";
import {HeaderComponent} from "../layouts/header/header.component";
import {AppScript} from "../../../assets/ts/scripts";

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    RouterOutlet,
    FooterComponent,
    SidebarComponent,
    HeaderComponent
  ],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent implements AfterViewInit{
    ngAfterViewInit(): void {
        AppScript.init();
    }

}
