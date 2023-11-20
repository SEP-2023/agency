import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AgencyService} from "../../service/transaction.service";

@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.css']
})
export class OffersComponent implements OnInit {

  constructor(private router: Router,private agencyService: AgencyService) { }

  ngOnInit(): void {}

  goToLink(url: string){
    this.agencyService
      .createTransaction("400", false)
      .subscribe(
        (data) => {
          window.location.href = `${url}?price=400&transactionId=${data.transactionId}&agencyId=nekiId`;
        },
        (error) => {
          console.log(error);
          alert('Greska');
        }
      );
    //window.open(url, "_blank");
  }

  goToSubscriptionLink(url: string){
    this.agencyService
      .createTransaction("400", true)
      .subscribe(
        (data) => {
          window.location.href = `${url}?price=1000&transactionId=${data.transactionId}&agencyId=nekiId&frequency=year`;
        },
        (error) => {
          console.log(error);
          alert('Greska');
        }
      );
   // window.open(url, "_blank");
  }

}
