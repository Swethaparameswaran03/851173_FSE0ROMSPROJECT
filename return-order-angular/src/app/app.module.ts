import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './component/login/login.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap'; 
import { HttpClientModule} from '@angular/common/http'; 
import { FormsModule } from '@angular/forms'; 
import { ProcessComponent } from './component/process/process.component'; 
import { BnNgIdleService } from 'bn-ng-idle'; 
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ProcessComponent
  ],
  imports: [ 
    BrowserModule, 
    AppRoutingModule, 
     NgbModule, 
    HttpClientModule, 
     FormsModule 
  ], 
  providers: [BnNgIdleService], 
  bootstrap: [AppComponent] 
}) 
export class AppModule { } 
