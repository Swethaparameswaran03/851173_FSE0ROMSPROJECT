import { HttpClient, HttpHeaders, JsonpClientBackend } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/service/login.service';
import { Validators } from '@angular/forms';
import { FormsModule } from '@angular/forms';


export class Payment {
  processid=''
  requestid=''
  cardnumber=''
  processingCharge=''
}
@Component({
  selector: 'app-process',
  templateUrl: './process.component.html',
  styleUrls: ['./process.component.css']
})

export class ProcessComponent implements OnInit {
  
  payment={
    processid:'',
    requestid:'',
    cardnumber:'',
    creditLimit:'',
    processingCharge:''
  }
  paymentList:Payment[]=[]
  homenav=''
  msgError=''
  currentview='home'
  processrequest={
    name:'',
    userid:'',
    contactno:'',
    componentType:'',
    componentName:'',
    quantity:'',
    priority:''
  }
  processresponse={
    requestId:'',
    processingCharge:'',
    packagingAndDeliveryCharge:'',
    dateOfDelivery:''
  }
  paymentDone=''
  msgForReturnForm=''
  isAuthorized=false;
  requestnav=''
  reqListnav=''
  constructor(private loginService:LoginService,private http: HttpClient) { }
  ngOnInit(): void {
    this.homenav='nav-link active'
    this.requestnav='nav-link'
    this.reqListnav='nav-link'
    this.loginService.checkValidity().subscribe(
      request=>{},
      error=>{
        console.log(error.error.text);
        if(error.error.text==="Valid")
          this.isAuthorized=true
      }
    )
    console.log(this.isAuthorized)
    this.paymentDone=''
    this.currentview='home'
  }
  checkIfValid()
  {
    this.loginService.checkValidity().subscribe(
      (response:any)=>{
          // console.log(response.error);
      },
      error=>{
          console.log(error.error.text);
          if(error.error.text==="Valid")
            this.isAuthorized=true
      });
  }
  returnreqSubmit(){
    if(this.isFilled()){
      this.checkIfValid()
      let url = "http://localhost:8080/process"
      let token =localStorage.getItem("token")  
      const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': token!=null?token:''
      }), };
       this.http.post(`${url}/processdetail`,this.processrequest,httpOptions).subscribe(
      (response:any)=>{
          console.log(<any>response);
          this.processresponse=response
      },
      error=>{
          console.log(error)
          throw error;
      });
      this.currentview='processview'
    }
    else{
      this.msgError='All Fields are mandatory'
    } 
    
  }
  clearForm() {
   this.processrequest.name =' ',
   this.processrequest.contactno =' ',
   this.processrequest.componentType =' ',
   this.processrequest.componentName= '',
   this.processrequest.quantity = ' ';
  }
  returnReq(){
    this.currentview='returnreqview'
    this.requestnav='nav-link active'
    this.homenav='nav-link'
    this.reqListnav='nav-link'
  }
  confirmPaymentDetails() {
    this.checkIfValid()
    this.payment.processingCharge=this.processresponse.packagingAndDeliveryCharge+this.processresponse.processingCharge
    this.currentview='paymentview'
  }
  goBackToConfirmPage() {
    this.currentview='processview'
  }
  makePayment() {
    this.checkIfValid()
    this.payment.requestid=this.processresponse.requestId
    this.payment.processingCharge=this.processresponse.packagingAndDeliveryCharge+this.processresponse.processingCharge
    console.log(this.payment.processingCharge)
    this.loginService.makePayment(this.payment).subscribe(
      (response:any)=>{
      },
      error=>{
       this.paymentDone=error.error.text
      });

  }

  isFilled(){
      
    return true;
  }

  goHome(){
    this.currentview='home'
    this.homenav='nav-link active'
    this.requestnav='nav-link'
    this.reqListnav='nav-link'
  }
  
  viewRequestList(){
    this.currentview='reqlist'
    this.homenav='nav-link'
    this.requestnav='nav-link'
    this.reqListnav='nav-link active'
    this.loginService.getAllCompletedProcess().subscribe(
      data=>{
         console.log(data)
         this.paymentList= <Payment[]>data;
      } 
    )
  }
  logout():void {
    this.loginService.logOutUser()
    window.location.href=''
  }

}
