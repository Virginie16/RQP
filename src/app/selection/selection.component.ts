import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { ApiHttpService } from '../core/services/api-http.service';
import { ApiEndpointsService } from '../core/services/api-endpoints.service';
import { NgFor, DatePipe } from '@angular/common';
import { Matiere } from '../matieres';
import { jsPDF } from 'jspdf';
import html2canvas from 'html2canvas';

@Component({
  selector: 'app-selection',
  imports: [NgFor, DatePipe],
  templateUrl: './selection.component.html',
  styleUrl: './selection.component.scss',
})
export class SelectionComponent {
  matieres: Matiere[] = [];

  filteredMatieres = this.matieres;
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
        this.filteredMatieres = this.matieres.filter(
          (matiere) => matiere.Commentaires !== 'N/A',
        );
      });
  }

  exportPDF() {
    const data = document.getElementById('filtered-content');
    html2canvas(data!).then((canvas) => {
      const imgWidth = 208;
      const imgHeight = (canvas.height * imgWidth) / canvas.width;
      const contentDataURL = canvas.toDataURL('image/png');
      const pdf = new jsPDF('p', 'mm', 'a4'); // A4 size page of PDF
      const position = 0;
      pdf.addImage(contentDataURL, 'PNG', 0, position, imgWidth, imgHeight);
      pdf.save('filtered-data.pdf'); // Save the generated PDF
    });
  }
  navigateToAccueil() {
    this.router.navigate(['/allMatiere']);
  }
}
