import { Component } from '@angular/core'; 
import { BnNgIdleService } from 'bn-ng-idle'; 
@Component({ 
  selector: 'app-root', 
  templateUrl: './app.component.html', 
  styleUrls: ['./app.component.css'] 
}) 
export class AppComponent { 
  title = 'return-order-angular'; 
  expirationTimeInSeconds=300;  // session will get expired after 5 mintues of inactivty 
  constructor(private bnIdle: BnNgIdleService) {  
    this.bnIdle.startWatching(this.expirationTimeInSeconds).subscribe((res: any) => { 
      if(res) { 
          console.log("session expired"); 
          localStorage.removeItem("token") 
            window.location.href='' 
      } 
    }) 
  } 
} 

 

 
