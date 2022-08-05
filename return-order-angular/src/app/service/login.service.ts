import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  validateMsg='Invalid';
  url = "http://localhost:8080/auth"
constructor(private http:HttpClient) { }

  generateToken(credentials:any) {
    
    return this.http.post(`${this.url}/authenticate`,credentials)
  }

  //for login user
  loginUser(token: any)
  {
    localStorage.setItem("token",token)
    return true;
  }
  checkValidity()
  {
    let token =localStorage.getItem("token")
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': token!=null?token:'',
      }), };
    return this.http.post(`${this.url}/validate`,null,httpOptions)

  }
  logOutUser(){
    localStorage.removeItem("token")
    return true;
  }
  makePayment(payment:any) {
    let url = "http://localhost:8080/process"
    
      let token =localStorage.getItem("token") 
      const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': token!=null?token:'',
      }), };
      console.log(token)
      return this.http.post(`${url}/CompleteProcessing`,payment,httpOptions)
  }
  getAllCompletedProcess() {
    let url = "http://localhost:8080/process"
    
      let token =localStorage.getItem("token") 
      const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': token!=null?token:'',
      }), };
      console.log(token)
      return this.http.get(`${url}/processlist`,httpOptions)
  }
  

}
