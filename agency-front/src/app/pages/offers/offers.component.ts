import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AgencyService} from "../../service/transaction/transaction.service";
import {AuthenticationService} from "../../service/authentication/authentication.service";
import {PaymentService} from "../../service/payment/payment.service";
import {AgencyInfoDto} from "../../model/agencyInfoDto";

@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.css']
})
export class OffersComponent implements OnInit {

  constructor(private router: Router,private agencyService: AgencyService,private authenticationService: AuthenticationService,private paymentService:PaymentService) { }

  ngOnInit(): void {
    this.authenticationService.getCurrentUser().subscribe({
      next(data)
        {console.log(data)},
      error(data){
        console.log(data)
      }});
  }

  goToLink(url: string){
    this.agencyService
      .createTransaction("400", false)
      .subscribe(
        (data) => {
          window.location.href = `${url}?price=400&transactionId=${data.transactionId}&agencyId=nekiId`;
          //gadjamo psp bek
          // var info = new AgencyInfoDto('nekiId',data.transactionId,"400",sessionStorage.getItem("accessToken")??"");
          // this.paymentService.createToken(info).subscribe({
          //     next(data)
          //     {console.log(data);
          //       window.location.href = `${url}?price=400&transactionId=${data.transactionId}&agencyId=nekiId&token=${data.token}`;
          //       },
          //     error(data){
          //       console.log(data)
          //     }}
          // )
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
