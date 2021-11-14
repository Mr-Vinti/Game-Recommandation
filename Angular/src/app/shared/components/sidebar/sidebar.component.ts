import { Component, Input, OnInit } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { ROUTE } from '../../constants/routes-constants';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent implements OnInit {
  @Input() sidenav: MatSidenav;

  public menu = [
    {
      title: 'Template',
      route: [ROUTE.TEMPLATE.TEMPLATE.PATH],
      icon: ['explore'],
      class: 'parent-none',
    },
  ];

  constructor(
  ) {
  }

  async ngOnInit() {
    this.menu = this.menu.filter((menuItem) => menuItem);
  }
}
