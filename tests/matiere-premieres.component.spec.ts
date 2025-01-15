import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MatierePremieresComponent } from './matiere-premieres.component';

describe('MatierePremieresComponent', () => {
  let component: MatierePremieresComponent;
  let fixture: ComponentFixture<MatierePremieresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MatierePremieresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MatierePremieresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
