import { CanActivateFn } from '@angular/router';

export const negateAuthGuard: CanActivateFn = (route, state) => {
  return true;
};
