import { BrowserModule, bootstrapApplication } from '@angular/platform-browser';

import { AppComponent } from './app/app.component';
import { ApiHttpService } from './app/core/services/api-http.service';
import { ApiEndpointsService } from './app/core/services/api-endpoints.service';
import { Constants } from './app/config/constants';
import { ReactiveFormsModule } from '@angular/forms';
import {
  withInterceptorsFromDi,
  provideHttpClient,
} from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { importProvidersFrom } from '@angular/core';
import { MatierePremieresComponent } from './app/matiere-premieres/matiere-premieres.component';

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(BrowserModule, ReactiveFormsModule),
    ApiHttpService,
    ApiEndpointsService,
    Constants,
    provideHttpClient(withInterceptorsFromDi()),
    provideRouter([{ path: '', component: MatierePremieresComponent }]),
  ],
}).catch((err) => console.error(err));
