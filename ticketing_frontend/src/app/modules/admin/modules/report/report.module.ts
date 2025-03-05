import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportRoutingModule } from './report-routing.module';
import {ReportComponent} from "./report.component";
import {NgbInputDatepicker, NgbPagination} from "@ng-bootstrap/ng-bootstrap";
import {NzCardComponent} from "ng-zorro-antd/card";
import {NzBreadCrumbComponent, NzBreadCrumbItemComponent} from "ng-zorro-antd/breadcrumb";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {NzButtonComponent} from "ng-zorro-antd/button";
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    ReportComponent
  ],
  imports: [
    CommonModule,
    ReportRoutingModule,
    NgbPagination,
    NzCardComponent,
    NzBreadCrumbComponent,
    NzBreadCrumbItemComponent,
    NzIconDirective,
    NzButtonComponent,
    NgbInputDatepicker,
    ReactiveFormsModule,
  ]
})
export class ReportModule { }
