import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { UserService } from './services/services/user.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, MatToolbarModule, MatSnackBarModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'blog-site-ui';
  hideMain: boolean = false;
  logOutButton: boolean = false;

  constructor(private router: Router, private snackBar:MatSnackBar, private userService:UserService) { }

  ngOnInit() {
    this.hideMain = false;
  }

  navigateToSignUp() {
    this.hideMain = true;
    this.router.navigate(['/signup']);
  }

  navigateToSignIn() {
    this.hideMain = true;
    this.router.navigate(['/signin']);
  }

  navigateToBlogs() {
    this.hideMain = true;
    this.router.navigate(['/blogs']);
  }
}
