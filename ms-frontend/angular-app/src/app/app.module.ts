import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component'; // Importez HeaderComponent

@NgModule({
  declarations: [
    AppComponent // Déclarez uniquement AppComponent ici
  ],
  imports: [
    BrowserModule,
    HeaderComponent // Importez HeaderComponent ici
  ],
  providers: [],
  bootstrap: [AppComponent] // Composant racine
})
export class AppModule {}
