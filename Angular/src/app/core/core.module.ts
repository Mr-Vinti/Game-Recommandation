import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TestserviceService } from './http/testrest/testservice.service';
import { CommonService } from './http/common.service';

// Services

// components

@NgModule({
  declarations: [],
  imports: [CommonModule],
  exports: [CommonModule],
  providers: [
    TestserviceService,
    CommonService,
  ],
})
export class CoreModule {}
