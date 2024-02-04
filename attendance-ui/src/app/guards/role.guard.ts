import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const roleGuard: CanActivateFn = (route, state) => {
  const role = localStorage.getItem('role');
  const router = inject(Router);

  if (role !== 'ADMIN') {
    router.navigate(['/access-denied']);
  }

  // if (state.url.startsWith('/log-mgnt') && role == 'ADMIN') {
  //   router.navigate(['/access-denied']);
  //   return false;
  // }

  return true;
};
