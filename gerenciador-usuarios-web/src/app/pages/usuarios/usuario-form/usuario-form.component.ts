import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatChipsModule } from '@angular/material/chips';
import { Usuario } from '../../../models/usuario/usuario.model';
import { Perfil } from '../../../models/perfil/perfil.model';
import { UsuarioService } from '../../../services/usuario.service';
import { PerfilService } from '../../../services/perfil.service';
import { ToastService } from '../../../services/toast.service';
import { ModelValidators } from '../../../models/validators';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-usuario-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatChipsModule
  ],
  templateUrl: './usuario-form.component.html',
  styleUrls: ['./usuario-form.component.scss']
})
export class UsuarioFormComponent {
  form: FormGroup;
  perfis: Perfil[] = [];

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<UsuarioFormComponent>,
    private usuarioService: UsuarioService,
    private perfilService: PerfilService,
    private toast: ToastService,
    @Inject(MAT_DIALOG_DATA) public data: Usuario & { readonly: boolean }
  ) {
    this.data = { ...data, readonly: data?.readonly || false };
    this.form = this.fb.group(ModelValidators.usuarioFormValidator());

    this.carregarPerfis().then(() => {
      if (data?.id) {
        this.form.patchValue({
          nome: data.nome,
          perfis: data.perfis
        });
      }

      if (this.data.readonly) {
        this.form.disable();
      }
    });
  }

  async carregarPerfis() {
    try {
      const response = await this.perfilService.listar().toPromise();
      if (response) {
        this.perfis = response.content;
      }
    } catch (error) {
      this.toast.error(error as HttpErrorResponse);
    }
  }

  onSubmit() {
    if (this.form.valid) {
      const formValue = this.form.value;
      const usuarioDTO = {
        nome: formValue.nome,
        perfis: formValue.perfis.map((p: Perfil) => p.id)
      };

      const request = this.data.id ?
        this.usuarioService.atualizar(this.data.id, usuarioDTO) :
        this.usuarioService.criar(usuarioDTO);

      request.subscribe({
        next: (response) => {
          if (this.toast.handleResponse(response, 'Usuário')) {
            this.dialogRef.close(true);
          }
        },
        error: (error) => this.toast.error(error)
      });
    }
  }

  onCancel() {
    this.dialogRef.close();
  }

  comparePerfis(perfil1: Perfil, perfil2: Perfil): boolean {
    return perfil1?.id === perfil2?.id;
  }
}
