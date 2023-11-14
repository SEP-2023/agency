import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { environment } from "src/environments/environment"

@Injectable({
  providedIn: 'root'
})
export class AgencyService {

  constructor(private _http: HttpClient) {}

  url = environment.agency_service_url;

  createTransaction(amount:string, paymentType: string){
    let body = {
      amount:amount,
      paymentType: paymentType,
      agencyId: "nekiAgency",
      currency: "USD"
    }
    return this._http.post<any>(`${this.url}/create-transaction`,body, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });

  }
}
