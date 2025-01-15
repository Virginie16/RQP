import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TopBarComponent } from './top-bar/top-bar.component';
import { MatierePremieresComponent } from './matiere-premieres/matiere-premieres.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TopBarComponent, MatierePremieresComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = 'RQP';
}
