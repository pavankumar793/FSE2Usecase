import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { BlogService } from './services/services/blog.service';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, MatToolbarModule, MatSnackBarModule, MatIconModule, MatButtonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'blog-site-ui';
  hideMain: boolean = false;
  userInfo: boolean = false;
  userInfoName: string = "";

  constructor(private router: Router, private snackBar:MatSnackBar, private blogService:BlogService) { }

  ngOnInit() {
    this.hideMain = false;
    if (this.blogService.getUserName() != "") {
      this.userInfo = true;
      this.userInfoName = this.blogService.getUserName();
    }
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
