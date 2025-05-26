import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UsuarioFormComponent } from './usuario-form.component';
import { UsuarioService } from '../../../services/usuario.service';
import { PerfilService } from '../../../services/perfil.service';
import { ToastService } from '../../../services/toast.service';

describe('UsuarioFormComponent', () => {
  let component: UsuarioFormComponent;
  let fixture: ComponentFixture<UsuarioFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        NoopAnimationsModule,
        UsuarioFormComponent
      ],
      providers: [
        { provide: UsuarioService, useValue: {} },
        { provide: PerfilService, useValue: {} },
        { provide: MatDialogRef, useValue: {} },
        { provide: MAT_DIALOG_DATA, useValue: { readonly: false } },
        { provide: ToastService, useValue: {} }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(UsuarioFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
