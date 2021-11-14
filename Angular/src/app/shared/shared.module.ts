import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmDialogComponent } from './components/confirm-dialog/confirm-dialog.component';
import { MaterialModule } from './material.module';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { AppRoutingModule } from '../modules/app-routing.module';
import { ThemePickerComponent } from './components/theme-picker';
import { StyleManagerComponent } from './components/style-manager';
import { ThemeStorageComponent } from './components/theme-storage/theme-storage.component';

@NgModule({
  declarations: [
    ConfirmDialogComponent,
    SidebarComponent,
    ThemePickerComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    AppRoutingModule,
  ],
  exports: [
    CommonModule,
    MaterialModule,
    ConfirmDialogComponent,
    SidebarComponent,
    ThemePickerComponent,
  ],
  providers: [StyleManagerComponent, ThemeStorageComponent],
})
export class SharedModule {}
