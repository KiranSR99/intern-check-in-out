// import { Injectable } from '@angular/core';
// import { Router } from '@angular/router';

// @Injectable({
//   providedIn: 'root',
// })
// export class RouteAccessService{
//   userRole: any;

//   routes: RouteItem[] = [
//     { path: '/app/user-mgnt/user-list', roles: ['ADMIN'] },
//     { path: '/app/user-mgnt/add-user', roles: ['ADMIN'] },
//     { path: '/app/user-mgnt/update-user/', roles: ['ADMIN'] },
//     { path: '/app/user-mgnt/show-user', roles: ['INTERN', 'SUPERVISOR'] },
//     { path: '/app/log-mgnt/add-log', roles: ['INTERN'] },
//     { path: '/app/log-mgnt/edit-log', roles: ['INTERN'] },
//     { path: '/app/log-mgnt/leave-request', roles: ['INTERN'] },
//     { path: '/app/log-mgnt/create-new', roles: ['INTERN'] },
//     { path: '/app/log-mgnt/my-log', roles: ['INTERN'] },
//     { path: '/app/log-mgnt/leave-request-list', roles: ['SUPERVISOR', 'ADMIN'] }
//   ];
  

//   constructor(private router: Router) {}

//   checkRoutePermission(routePath: string): boolean {
//     const route = this.routes?.find((r) => r.path === routePath);
//     if (route) {
//       this.userRole = localStorage.getItem('role');
//       if (this.userRole) {
//         this.userRole = JSON.parse(this.userRole);
//       }

//       return route.roles.includes(this.userRole);
//     }
//     return true;
//   }

//   navigateToRoute(routePath: string): void {
//     if (this.checkRoutePermission(routePath)) {
//       this.router.navigate([routePath]);
//     } else {
//       this.router.navigate(['/access-denied']);
//     }
//   }
// }
