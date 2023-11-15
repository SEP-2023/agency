import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {OffersComponent} from "./pages/offers/offers.component";

const routes: Routes = [
  { path: '', component: OffersComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
