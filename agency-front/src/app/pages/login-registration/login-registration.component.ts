import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {LoginInfo, User} from "../../model/user";
import {lastValueFrom} from "rxjs";
import {AuthenticationService} from "../../service/authentication/authentication.service";

@Component({
  selector: 'app-login-registration',
  templateUrl: './login-registration.component.html',
  styleUrls: ['./login-registration.component.css']
})
export class LoginRegistrationComponent implements OnInit {
  showLogin: boolean = true;
  user: User = new User();
  loginInfo: LoginInfo = new LoginInfo();
  loginData: any;
  form: FormGroup = new FormGroup({});
  isRegistered: boolean = false;

  constructor(private formBuilder: FormBuilder,private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
  }

  async loginUser() {
    if (this.loginInfo.email === '' || this.loginInfo.password === '') {
      //this.toastr.error('Please fill in all fields!');
      alert('Please fill in all fields!')
    } else {
      await this.login();
    }
  }

  async login() {
    try {
      const data = await lastValueFrom(
        this.authenticationService.loginUser(this.loginInfo)
      );
      console.log(data);
      if (data == null) {
        //this.toastr.error('Login failed', 'Upss..');
        alert('Login failed')
        return;
      }
      this.loginData = data;
    } catch (error) {
      //this.toastr.error('Wrong username or password', 'Upss..');
      alert('Wrong username or password')
    }
  }


  registerClick() {
    this.showLogin = false;
  }

  registerUser() {
    console.log(this.user)
    if(this.user.name == '' || this.user.surname == ''){
      alert('Please enter all fields.');
      return;
    }
    this.isRegistered = true;

    this.authenticationService.registerUser(this.user).subscribe({
      next: (data) => {
        //this.toastr.success('Activation email is sent', 'Info');
        alert('register')
        this.isRegistered = false;
      },
      error: (err) => {
        if (err.status == 400)
         // this.toastr.error('Use a stronger password.', 'Upss..');
          alert('Use a stronger password.')
        else //this.toastr.error('Something went wrong', 'Upss..');
        alert('Something went wrong')
        this.isRegistered = false;
      },
    });
  }

}
