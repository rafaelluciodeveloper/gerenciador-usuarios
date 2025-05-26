import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'usuarios',
    loadComponent: () => import('./pages/usuarios/usuarios.component').then(m => m.UsuariosComponent)
  },
  {
    path: 'perfis',
    loadComponent: () => import('./pages/perfis/perfis.component').then(m => m.PerfisComponent)
  },
  {
    path: '',
    redirectTo: 'usuarios',
    pathMatch: 'full'
  }
];
