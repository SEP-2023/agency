import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {AgencyInfoDto} from "../../model/agencyInfoDto";

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private _http: HttpClient) {}

  url = environment.psp_service_url;

  createToken(info:AgencyInfoDto){
    return this._http.post<any>(`${this.url}/get-token`,info, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });

  }
}
