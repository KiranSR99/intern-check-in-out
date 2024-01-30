import { Component } from '@angular/core';
import { RoleEnum } from '../../core/enums/role.enum';

@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrl: './side-nav.component.scss'
})
export class SideNavComponent {

  menus: Array<any> = new Array<any>(
    {
      title: 'Dashboard',
      roleAccess: [RoleEnum.ALL],
      url: '/app/dashboard',
      icon: 'fa-solid fa-home',
    },
    {
      title: 'User Management',
      roleAccess: [RoleEnum.ADMIN],
      url: '/app/user-mgnt',
      icon: 'fa-solid fa-user',
    },
    {
      title: 'Log Management',
      roleAccess: [RoleEnum.INTERN],
      url: '/app/log-mgnt/log-list',
      icon: 'fa-solid fa-list-check',
    },
  );

}
