import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Perfil } from '../../../models/perfil/perfil.model';
import { PerfilService } from '../../../services/perfil.service';
import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-perfil-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule
  ],
  templateUrl: './perfil-form.component.html',
  styleUrls: ['./perfil-form.component.scss']
})
export class PerfilFormComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<PerfilFormComponent>,
    private perfilService: PerfilService,
    private toast: ToastService,
    @Inject(MAT_DIALOG_DATA) public data: Perfil & { readonly: boolean }
  ) {
    this.data = { ...data, readonly: data?.readonly || false };
    this.form = this.fb.group({
      descricao: ['', [Validators.required, Validators.minLength(5)]]
    });

    if (data?.id) {
      this.form.patchValue(data);
    }

    if (this.data.readonly) {
      this.form.disable();
    }
  }

  onSubmit() {
    if (this.form.valid) {
      const perfil: Perfil = {
        ...this.data,
        ...this.form.value
      };

      const request = perfil.id ?
        this.perfilService.atualizar(perfil.id, perfil) :
        this.perfilService.criar(perfil);

      request.subscribe({
        next: (response) => {
          if (this.toast.handleResponse(response, 'Perfil')) {
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
}
