import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {OffersComponent} from "./pages/offers/offers.component";
import {LoginRegistrationComponent} from "./pages/login-registration/login-registration.component";

const routes: Routes = [
  { path: '', component: OffersComponent },
  { path: 'offers', component: OffersComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
