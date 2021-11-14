/**
 *    Copyright 2016 Sven Loesekann
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';


// external angular components,modules,directives
import { AppComponent } from './app.component';

// core Module
import { CoreModule } from '../core/core.module';

// shared Module
import { SharedModule } from '../shared/shared.module';

// routes
import { AppRoutingModule } from './app-routing.module';
import { LoadingService } from '../core/http/loading-service';
import { LoadingInterceptor } from '../core/http/loading-interceptor';

// Material
import { CommonModule } from '@angular/common';
import { TemplateModule } from './template-module/template.module';


@NgModule({
  declarations: [AppComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    AppRoutingModule,
    CoreModule,
    SharedModule,
    TemplateModule,
    BrowserAnimationsModule,
  ],
  bootstrap: [AppComponent],
  providers: [
    [LoadingService],
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoadingInterceptor,
      multi: true,
    },
  ],
})
export class AppModule {}
