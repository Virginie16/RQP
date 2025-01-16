import { Component } from '@angular/core';
import { NgFor, DatePipe } from '@angular/common';
import { ApiHttpService } from '../core/services/api-http.service';
import { ApiEndpointsService } from '../core/services/api-endpoints.service';
import { Matiere } from '../matieres';
import { Router } from '@angular/router';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import {
  MAT_FORM_FIELD_DEFAULT_OPTIONS,
  MatFormFieldModule,
} from '@angular/material/form-field';

@Component({
  selector: 'app-matiere-premieres',
  imports: [
    MatFormFieldModule,
    MatTableModule,
    MatInputModule,
    NgFor,
    DatePipe,
  ],
  providers: [
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: { appearance: 'outline' },
    },
  ],
  templateUrl: './matiere-premieres.component.html',
  styleUrl: './matiere-premieres.component.scss',
})
export class MatierePremieresComponent {
  matieres: Matiere[] = [];
  displayedColumns: string[] = [
    'type',
    'nom',
    'fournisseur',
    'reference',
    'noLot',
    'noLotFournisseur',
    'acceptationLot',
    'cNC',
    'commentaires',
  ];

  dataSource = new MatTableDataSource(this.matieres);
  constructor(
    private apiHttpService: ApiHttpService,
    private apiEndpointsService: ApiEndpointsService,
    private router: Router,
  ) {}
  ngOnInit() {
    this.apiHttpService
      .get(this.apiEndpointsService.getProductsEndpoint())
      .subscribe((matieres: Matiere[]) => {
        this.matieres = matieres;
        this.dataSource.data = matieres;
      });
  }
  share() {
    window.alert('The product has been shared!');
  }
  navigateToAbout() {
    this.router.navigate(['/selection']);
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase(); // Assurez-vous que le filtre fonctionne
  }
}
