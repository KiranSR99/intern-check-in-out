import { Component, OnInit } from '@angular/core';
import {
  ActivatedRoute,
  NavigationEnd,
  NavigationStart,
  Router,
} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = 'attendance-ui';
  routes = ['/app/user-mgnt/user-list'];

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {}

  // ngOnInit(): void {
  //   this.router.events.subscribe((val) => {
  //     // see also
  //     console.log('on start: ', val instanceof NavigationStart);

  //     if (
  //       val instanceof NavigationStart &&
  //       !this.routes.includes(this.router.url)
  //     ) {
  //       this.router.navigate(['/access-denied']);
  //     }

  //     console.log('on end: ', val instanceof NavigationEnd);
  //   });
  // }
}
