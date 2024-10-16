import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { PatientDataComponent } from './patient-data/patient-data.component';

@NgModule({
  declarations: [
    AppComponent // Déclarez uniquement AppComponent ici
  ],
  imports: [
    BrowserModule,
    PatientDataComponent
  ],
  providers: [],
  bootstrap: [AppComponent] // Composant racine
})
export class AppModule {}
