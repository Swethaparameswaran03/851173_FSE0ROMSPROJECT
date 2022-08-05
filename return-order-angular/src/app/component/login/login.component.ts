import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/service/login.service';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginError=false;  
  credentials={
      username:'',
      password:''
    }
    constructor(private loginService:LoginService) { }
  
    ngOnInit(): void {
      this.loginError=false;
    }
  
    onSubmit()
    {
      
      if((this.credentials.username!=''&&this.credentials.password!='')||
      (this.credentials.username!=null&&this.credentials.password!=null)) {
      this.loginService.generateToken(this.credentials).subscribe(
        (response:any)=>{
            console.log(response.token);
            this.loginService.loginUser(response.token)
            window.location.href='/process'
        },
        error=>{
            console.log(error);
            this.loginError=true;
        })
        this.credentials.password=''
        this.credentials.username=''
      }
    }
    clearLoginForm()
    {
      this.credentials.username =' ',
      this.credentials.password =' ';
    }
  
}