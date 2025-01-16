import { Component } from '@angular/core';
import { NgFor } from '@angular/common';
import { ApiHttpService } from '../core/services/api-http.service';
import { ApiEndpointsService } from '../core/services/api-endpoints.service';
import { Matiere } from '../matieres';

@Component({
  selector: 'app-matiere-premieres',
  imports: [NgFor],
  templateUrl: './matiere-premieres.component.html',
  styleUrl: './matiere-premieres.component.scss',
})
export class MatierePremieresComponent {
  matieres: Matiere[] = [];

  constructor(
    private apiHttpService: ApiHttpService,
    private apiEndpointsService: ApiEndpointsService,
  ) {}
  ngOnInit() {
    this.apiHttpService
      .get(this.apiEndpointsService.getProductsEndpoint())
      .subscribe((matieres: Matiere[]) => {
        this.matieres = matieres;
      });
  }
  share() {
    window.alert('The product has been shared!');
  }
}
