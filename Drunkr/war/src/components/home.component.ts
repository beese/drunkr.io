import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

import { DrunkrService, UserService } from '../API/api.service';

@Component({
  selector: 'my-home',
  templateUrl: '/templates/home.html',
})
export class HomeComponent implements OnInit {

  constructor(
    private router: Router,
    private DrunkrService: DrunkrService,
    @Input() private UserService: UserService
  ) { }

  ngOnInit(): void {
    
  }
}