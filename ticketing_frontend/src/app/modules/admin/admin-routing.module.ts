import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AdminComponent} from "./admin.component";

const routes: Routes = [
  {
    path: "",
    component: AdminComponent,
    children: [
      {
        path: "dashboard",
        loadChildren: () => import('./modules/dashboard/dashboard.module').then(m => m.DashboardModule)
      },
      {
        path: "transactions",
        loadChildren: () => import('./modules/transaction/transaction.module').then(m => m.TransactionModule)
      },
      {
        path: "reports",
        loadChildren: () => import('./modules/report/report.module').then(m => m.ReportModule)
      },
      {
        path: "" ,
        redirectTo: "dashboard",
        pathMatch: "full"
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
