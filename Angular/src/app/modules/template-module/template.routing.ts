import { RouterModule, Routes } from '@angular/router';

// Components
import { TemplateComponent } from './template.component';

// Routes
const moduleRoutes: Routes = [
  {
    path: 'template',
    component: TemplateComponent,
  },
];

export const TemplateRouting = RouterModule.forRoot(moduleRoutes, {
  relativeLinkResolution: 'legacy',
});
