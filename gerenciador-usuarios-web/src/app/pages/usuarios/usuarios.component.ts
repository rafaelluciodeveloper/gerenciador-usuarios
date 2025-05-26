import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { UsuarioService } from '../../services/usuario.service';
import { Usuario } from '../../models/usuario/usuario.model';
import { UsuarioFormComponent } from './usuario-form/usuario-form.component';
import { ConfirmDialogComponent } from '../../shared/components/confirm-dialog/confirm-dialog.component';
import { PageEvent } from '@angular/material/paginator';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule
  ],
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.scss']
})
export class UsuariosComponent implements OnInit {
  usuarios: Usuario[] = [];
  colunas = ['nome', 'acoes'];
  totalElementos = 0;
  filtro = {
    page: 0,
    size: 10,
    nome: '',
    sort: ['nome,asc']
  };

  constructor(
    private usuarioService: UsuarioService,
    private dialog: MatDialog,
    private toast: ToastService
  ) {}

  ngOnInit() {
    this.carregarUsuarios();
  }

  carregarUsuarios() {
    this.usuarioService.listar(this.filtro).subscribe({
      next: response => {
        this.usuarios = response.content;
        this.totalElementos = response.totalElements;
      },
      error: error => this.toast.error(error)
    });
  }

  aplicarFiltro() {
    this.filtro.page = 0;
    this.carregarUsuarios();
  }

  onPaginaChange(event: PageEvent) {
    this.filtro.page = event.pageIndex;
    this.filtro.size = event.pageSize;
    this.carregarUsuarios();
  }

  abrirModal(usuario?: Usuario & { readonly?: boolean }) {
    const dialogRef = this.dialog.open(UsuarioFormComponent, {
      width: '600px',
      data: usuario
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.carregarUsuarios();
      }
    });
  }

  visualizar(usuario: Usuario) {
    this.abrirModal({ ...usuario, readonly: true });
  }

  editar(usuario: Usuario) {
    this.abrirModal(usuario);
  }

  confirmarExclusao(usuario: Usuario) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '400px',
      data: {
        titulo: 'Confirmar Exclusão',
        mensagem: `Deseja realmente excluir o usuário ${usuario.nome}?`
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.usuarioService.excluir(usuario.id).subscribe({
          next: (response) => {
            if (this.toast.handleResponse(response, 'Usuário')) {
              this.carregarUsuarios();
            }
          },
          error: (error) => this.toast.error(error)
        });
      }
    });
  }
}
