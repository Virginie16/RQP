import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CsvService } from '../csv.service';

@Component({
  selector: 'app-matiere-premieres',
  // standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './matiere-premieres.component.html',
  styleUrl: './matiere-premieres.component.scss',
})
export class MatierePremieresComponent implements OnInit {
  matieresPremieres: any[] = [
    { nom: 'Matière 1', fournisseur: 'Fournisseur 1' },
    { nom: 'Matière 2', fournisseur: 'Fournisseur 2' },
  ];
  articlesConditionnement: any[] = [
    { nom: 'Article 1', fournisseur: 'Fournisseur 1' },
    { nom: 'Article 2', fournisseur: 'Fournisseur 2' },
  ];
  data: any[] = [];
  displayedColumns: string[] = [];

  constructor(private CsvService: CsvService) {}

  ngOnInit() {
    this.CsvService.loadCsv('assets/extrait-RQP.csv').subscribe(
      (result: any[]) => {
        this.data = result;
        if (this.data.length) {
          this.displayedColumns = Object.keys(this.data[0]);
        }
      },
      (error: any) => {
        console.error('Error loading CSV', error);
      }
    );
  }
}
