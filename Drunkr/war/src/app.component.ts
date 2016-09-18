import { Component, OnInit, Input } from '@angular/core';
import { Observable } from 'rxjs/Rx';

import { User } from './API/api.models';
import { DrunkrService, UserService } from './API/api.service';

@Component({
  selector: 'my-app',
  templateUrl: '/templates/app.html',
  providers: [DrunkrService]
})
export class AppComponent implements OnInit {
  constructor(
    private DrunkrService: DrunkrService,
    @Input() private UserService: UserService
  ) { }

  public logOut() {
    this.DrunkrService.logout()
      .then(obj => {
        window.location.reload();
      });
  }

  ngOnInit() {
    
  }
}
