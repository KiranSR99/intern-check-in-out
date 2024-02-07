import { Component } from '@angular/core';
import { RoleEnum } from '../../core/enums/role.enum';
import { ToastService } from '../../services/toast.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrl: './side-nav.component.scss',
})
export class SideNavComponent {
  role: any;

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
      title: 'Users',
      roleAccess: [RoleEnum.INTERN, RoleEnum.SUPERVISOR],
      url: '/app/user-mgnt/show-user',
      icon: 'fa-solid fa-user',
    },
    {
      title: 'Log Management',
      roleAccess: [RoleEnum.INTERN, RoleEnum.SUPERVISOR],
      url: '/app/log-mgnt',
      icon: 'fa-solid fa-list-check',
    },
    {
      title: 'My Log',
      roleAccess: [RoleEnum.INTERN],
      url: '/app/log-mgnt/my-log',
      icon: 'fa-solid fa-book',
    },
    {
      title: 'Leave Request',
      roleAccess: [RoleEnum.INTERN, RoleEnum.SUPERVISOR],
      url: '/app/log-mgnt/leave-request',
      icon: 'fa-regular fa-calendar-xmark',
    }
  );

  constructor(private router: Router, private toast: ToastService) {}

  ngOnInit() {
    this.role = localStorage.getItem('role');

    if (this.role) {
      this.role = JSON.parse(this.role);
    }

    if (!this.role) return;
    console.log(this.role);
    // Filter main menus based on role
    this.menus = this.menus.filter(
      (v) =>
        v.roleAccess.includes(RoleEnum.ALL) || v.roleAccess.includes(this.role)
    );
  }

  onLogout() {
    this.toast.showSuccess('Logout Successful');
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  onClickMenu(menu: any) {
    if (menu.title === 'Leave Request' && this.role === RoleEnum.INTERN) {
      this.router.navigate(['/app/log-mgnt/leave-request']);
    } else if (
      menu.title === 'Leave Request' &&
      this.role === RoleEnum.SUPERVISOR
    ) {
      this.router.navigate(['/app/log-mgnt/leave-request-list']);
    } else {
      this.router.navigate([menu.url]);
    }
  }
}
