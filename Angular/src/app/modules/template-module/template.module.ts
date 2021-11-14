import { NgModule } from '@angular/core';
import { TemplateComponent } from './template.component';

import { TemplateRouting } from './template.routing';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  declarations: [TemplateComponent],
  imports: [
    SharedModule,
    TemplateRouting,
  ],
})
export class TemplateModule {}
