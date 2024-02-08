import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const role = localStorage.getItem('role');
    const path = route.routeConfig?.path;

    if (role === 'ADMIN' && this.isAdminRoute(path)) {
      return true;
    } else if (role === 'INTERN' && this.isInternRoute(path)) {
      return true;
    } else {
      this.router.navigate(['/access-denied']);
      return false;
    }
  }

  private isAdminRoute(path: string | undefined): any {
    return path === 'user-list' || path === 'add-user' || path?.startsWith('update-user');
  }

  private isInternRoute(path: string | undefined): boolean {
    return path === 'add-log' || path === 'edit-log' || path === 'leave-request' || path === 'create-new' || path === 'my-log';
  }
}
