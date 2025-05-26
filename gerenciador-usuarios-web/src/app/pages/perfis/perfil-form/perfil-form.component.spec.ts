import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { PerfilFormComponent } from './perfil-form.component';
import { PerfilService } from '../../../services/perfil.service';
import { ToastService } from '../../../services/toast.service';

describe('PerfilFormComponent', () => {
  let component: PerfilFormComponent;
  let fixture: ComponentFixture<PerfilFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        NoopAnimationsModule,
        PerfilFormComponent
      ],
      providers: [
        { provide: PerfilService, useValue: {} },
        { provide: MatDialogRef, useValue: {} },
        { provide: MAT_DIALOG_DATA, useValue: { readonly: false } },
        { provide: ToastService, useValue: {} }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(PerfilFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
