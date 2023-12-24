import { Injectable } from '@angular/core';
import {LoginInfo, User} from "../../model/user";
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders, HttpStatusCode} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  constructor(private _http: HttpClient) {}

  url = environment.agency_service_url + '/api/auth';

  registerUser(user: User) {
    const newUrl = this.url + '/register';
    return this._http.post<any>(newUrl, user,{
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });
  }

  loginUser(loginInfo: LoginInfo) {
    const newUrl = this.url + '/login';
    return this._http.post<any>(newUrl, loginInfo);
  }

  logout(token: string) {
    const newUrl = this.url + '/logout';
    return this._http.post<HttpStatusCode>(newUrl, token);
  }
}
