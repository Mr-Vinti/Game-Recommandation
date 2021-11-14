import {
  ChangeDetectionStrategy,
  Component,
  Input,
  OnDestroy,
  OnInit,
  ViewEncapsulation,
} from '@angular/core';
import { StyleManagerComponent } from '../style-manager/style-manager.component';
import {
  DocsSiteTheme,
  ThemeStorageComponent,
} from '../theme-storage/theme-storage.component';
import { MatIconRegistry } from '@angular/material/icon';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs';
import { map } from 'rxjs/operators';
import { DomSanitizer } from '@angular/platform-browser';
import { LiveAnnouncer } from '@angular/cdk/a11y';

@Component({
  selector: 'app-theme-picker',
  templateUrl: 'theme-picker.component.html',
  styleUrls: ['theme-picker.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  encapsulation: ViewEncapsulation.None,
})
export class ThemePickerComponent implements OnInit, OnDestroy {
  private _queryParamSubscription = Subscription.EMPTY;
  currentTheme: DocsSiteTheme;

  // The below colors need to align with the themes defined in theme-picker.scss
  themes: DocsSiteTheme[] = [
    {
      primary: '#3F51B5',
      accent: '#E91E63',
      displayName: 'Light Mode',
      name: 'indigo-pink',
      isDark: false,
      isDefault: true,
    },
    {
      primary: '#9C27B0',
      accent: '#4CAF50',
      displayName: 'Dark Mode',
      name: 'purple-green',
      isDark: true,
    },
  ];

  constructor(
    public styleManager: StyleManagerComponent,
    private _themeStorage: ThemeStorageComponent,
    private _activatedRoute: ActivatedRoute,
    private liveAnnouncer: LiveAnnouncer,
    iconRegistry: MatIconRegistry,
    sanitizer: DomSanitizer
  ) {
    iconRegistry.addSvgIcon(
      'theme-example',
      sanitizer.bypassSecurityTrustResourceUrl('assets/img/theme-demo-icon.svg')
    );
    const themeName = this._themeStorage.getStoredThemeName();
    if (themeName) {
      this.selectTheme(themeName);
    } else {
      if (
        window.matchMedia &&
        window.matchMedia('(prefers-color-scheme: dark)').matches
      ) {
        this.selectTheme('purple-green');
      } else {
        this.selectTheme('indigo-pink');
      }
    }

    window
      .matchMedia('(prefers-color-scheme: dark)')
      .addEventListener('change', (e) => {
        const turnOn = e.matches;
        this.selectTheme(turnOn ? 'purple-green' : 'indigo-pink');
      });
  }

  ngOnInit() {
    this._queryParamSubscription = this._activatedRoute.queryParamMap
      .pipe(map((params: ParamMap) => params.get('theme')))
      .subscribe((themeName: string | null) => {
        if (themeName) {
          this.selectTheme(themeName);
        }
      });
  }

  ngOnDestroy() {
    this._queryParamSubscription.unsubscribe();
  }

  selectTheme(themeName: string) {
    const theme = this.themes.find(
      (currentTheme) => currentTheme.name === themeName
    );

    if (!theme) {
      return;
    }

    this.currentTheme = theme;

    // if (theme.isDefault) {
    //   this.styleManager.removeStyle('theme');
    // } else {
    this.styleManager.setStyle('theme', `assets-${theme.name}.css`);
    // }

    if (this.currentTheme) {
      this.liveAnnouncer.announce(
        `${theme.displayName} theme selected.`,
        'polite',
        3000
      );
      this._themeStorage.storeTheme(this.currentTheme);
    }
  }
}
