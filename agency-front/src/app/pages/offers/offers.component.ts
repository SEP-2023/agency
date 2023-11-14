import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.css']
})
export class OffersComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }
  redirect(){
      this.router.navigate(['/project1']);
  }
  goToLink(url: string){
    window.open(url, "_blank");
  }

}
