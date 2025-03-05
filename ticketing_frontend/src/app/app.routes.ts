import {Routes} from '@angular/router';
import {AuthGuard} from "./services/auth/auth.guard";

export const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./modules/admin/admin.module').then(m => m.AdminModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'auth',
    loadChildren: () => import('./modules/auth/auth.module').then(m => m.AuthModule)
  }
];
