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
  reenterPass: string = '';

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
      sessionStorage.setItem('accessToken', this.loginData.accessToken);
      sessionStorage.setItem('expiresIn', this.loginData.expiresIn);
      window.location.href = '/offers';
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

    const emailValid = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.user.email);
    if (!emailValid){
      alert('Email is invalid.')
      return;
    }

    // Check for at least one uppercase letter
    const hasUppercase = /[A-Z]/.test(this.user.password);

    // Check for at least one number
    const hasNumber = /\d/.test(this.user.password);

    if (this.user.password.length < 8 || !hasNumber || !hasUppercase){
      alert('Password must contain number, upper letter and be minimum 8 characters long.')
      return;
    }

    if (this.user.password != this.reenterPass){
      alert('Passwords must be same.')
      return;
    }

    this.isRegistered = true;

    this.authenticationService.registerUser(this.user).subscribe({
      next: (data) => {
        alert('Successfully registered!')
        this.isRegistered = false;
      },
      error: (err) => {
        if (err.status == 400)
          alert('Use a stronger password.')
        else
        alert('Something went wrong')
        this.isRegistered = false;
      },
    });
  }

}
