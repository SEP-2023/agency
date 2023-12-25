import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { environment } from "src/environments/environment"

@Injectable({
  providedIn: 'root'
})
export class AgencyService {

  constructor(private _http: HttpClient) {}

  url = environment.agency_service_url;

  createTransaction(amount:string, isSubscription: boolean){
    let body = {
      amount:amount,
      agencyId: "nekiAgency",
      currency: "USD",
      isSubscription: isSubscription
    }
    return this._http.post<any>(`${this.url}/create-transaction`,body, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ` + sessionStorage.getItem('accessToken')
      })
    });

  }
}
