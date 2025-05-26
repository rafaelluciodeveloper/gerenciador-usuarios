import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { PerfilService } from '../../services/perfil.service';
import { Perfil } from '../../models/perfil/perfil.model';
import { PerfilFormComponent } from './perfil-form/perfil-form.component';
import { ConfirmDialogComponent } from '../../shared/components/confirm-dialog/confirm-dialog.component';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-perfis',
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
  templateUrl: './perfis.component.html',
  styleUrls: ['./perfis.component.scss']
})
export class PerfisComponent implements OnInit {
  perfis: Perfil[] = [];
  colunas = ['descricao', 'acoes'];
  totalElementos = 0;
  filtro = {
    page: 0,
    size: 10,
    descricao: '',
    sort: ['descricao,asc']
  };

  constructor(
    private perfilService: PerfilService,
    private dialog: MatDialog,
    private toast: ToastService
  ) {}

  ngOnInit() {
    this.carregarPerfis();
  }

  carregarPerfis() {
    this.perfilService.listar(this.filtro).subscribe({
      next: response => {
        this.perfis = response.content;
        this.totalElementos = response.totalElements;
      },
      error: error => this.toast.error(error)
    });
  }

  aplicarFiltro() {
    this.filtro.page = 0;
    this.carregarPerfis();
  }

  onPaginaChange(event: PageEvent) {
    this.filtro.page = event.pageIndex;
    this.filtro.size = event.pageSize;
    this.carregarPerfis();
  }

  abrirModal(perfil?: Perfil & { readonly?: boolean }) {
    const dialogRef = this.dialog.open(PerfilFormComponent, {
      width: '600px',
      data: perfil
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.carregarPerfis();
      }
    });
  }

  visualizar(perfil: Perfil) {
    this.abrirModal({ ...perfil, readonly: true });
  }

  editar(perfil: Perfil) {
    this.abrirModal(perfil);
  }

  confirmarExclusao(perfil: Perfil) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '400px',
      data: {
        titulo: 'Confirmar Exclusão',
        mensagem: `Deseja realmente excluir o perfil ${perfil.descricao}?`
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.perfilService.excluir(perfil.id).subscribe({
          next: (response) => {
            if (this.toast.handleResponse(response, 'Perfil')) {
              this.carregarPerfis();
            }
          },
          error: (error) => this.toast.error(error)
        });
      }
    });
  }
}
