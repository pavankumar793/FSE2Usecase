import { Component, ViewChild } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import { MatRadioModule } from '@angular/material/radio';
import { FormsModule, NgForm, ReactiveFormsModule } from '@angular/forms';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatButtonModule } from '@angular/material/button';
import { UserService } from '../services/services/user.service';
import { UserRegister } from '../models/user.model';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatSnackBarModule, MatButtonModule, MatNativeDateModule, FormsModule, MatRadioModule, MatDatepickerModule, MatToolbarModule, MatCardModule, MatFormFieldModule, MatInputModule],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css'
})
export class SignUpComponent {
  @ViewChild('userForm') userForm!: NgForm;
  userFormValidation!: FormGroup;
  hideMain: boolean = false;
  constructor(private userService:UserService, private snackBar:MatSnackBar, private fb: FormBuilder, private router: Router) {
    this.userFormValidation = this.fb.group({
      fnameValidator: ['', Validators.required],
      lnameValidator: ['', Validators.required],
      addressValidator: ['', Validators.required],
      usernameValidator: ['', Validators.required],
      emailValidator: ['', [Validators.required, Validators.email]],
      passwordValidator: ['', [Validators.required]],
      genderValidator: ['', Validators.required],
      dateOfBirthValidator: ['', Validators.required],
    });
   }

  lname: string = "";
  fname: string = "";
  address: string = "";
  email: string = "";
  username: string = "";
  password: string = "";
  gender: string = "";
  dateOfBirth: Date = new Date();
  user: UserRegister= new UserRegister();

  ngOnInit() {
    this.hideMain = false;
  }

  register() {
    console.log('Registering user');
    this.user.name = this.fname + ' ' + this.lname;
    this.user.address = this.address;
    this.user.email = this.email;
    this.user.username = this.username;
    this.user.password = this.password
    this.user.gender = this.gender;
    this.user.dateOfBirth = this.dateOfBirth;
    this.userService.register(this.user).subscribe({
      next: (message) => {
        this.snackBar.open('User registered successfully!', 'Close', {
          duration: 3000,
          panelClass: ['success-snackbar']
        });
        this.userForm.reset();
        this.router.navigate(['/signin']);
      },
      error: (error) => {
        console.error('Registration failed:', error);
        let errorMessage = 'Registration failed.'; // Default message
        if (error && error.message) {
          errorMessage = error.message; // Use server's error message if available
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
    this.router.navigate(['/home']);
  }
}
