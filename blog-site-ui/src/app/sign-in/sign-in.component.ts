import { Component, ViewChild, viewChild } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormBuilder, FormGroup, FormsModule, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { UserService } from '../services/services/user.service';
import { UserLogin } from '../models/user.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { BlogService } from '../services/services/blog.service';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatToolbarModule, MatCardModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, MatIconModule, MatButtonModule],
  templateUrl: './sign-in.component.html',
  styleUrl: './sign-in.component.css'
})
export class SignInComponent {
  @ViewChild('userForm') userForm!: NgForm;
  userFormValidation!: FormGroup;
  hideMain: boolean = false;
  user: UserLogin= new UserLogin();
  userInfo: boolean = false;
  userInfoName: string = "";

  constructor(private userService:UserService, private blogService:BlogService, public snackBar:MatSnackBar, private fb: FormBuilder, private router: Router) {
    this.userFormValidation = this.fb.group({
      usernameOrEmailValidator: ['', Validators.required],
      passwordValidator: ['', [Validators.required]]
    });
   }

   ngOnInit() {
    this.hideMain = false;
    if (localStorage.getItem('currentUser')) {
      this.router.navigate(['/userblogs']);
    }
    if (this.blogService.getUserName() != "") {
      this.userInfo = true;
      this.userInfoName = this.blogService.getUserName();
    }
   }

  login() {
    this.user.usernameOrEmail = this.userForm.value.usernameOrEmailValidator;
    this.user.password = this.userForm.value.passwordValidator;
    this.userService.login(this.user).subscribe({
      next: (message) => {
        this.snackBar.open('User Logged In successfully!', 'Close', {
          duration: 3000,
          panelClass: ['success-snackbar']
        });
        this.blogService.setUserName(this.userForm.value.usernameOrEmailValidator);
        this.router.navigate(['/userblogs']);
      },
      error: (error) => {
        console.error('Login failed:', error);
        let errorMessage = 'Login failed.';
        if (error && error.message) {
          errorMessage = error.message;
        }
        this.snackBar.open(errorMessage, 'Close', {
          duration: 3000,
          panelClass: ['error-snackbar']
        });
      }
    });
  }
  onHomeIconClick() {
    this.hideMain = false;
    this.router.navigate(['/']);
  }
}
