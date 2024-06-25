import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SignInComponent } from './sign-in.component';
import { UserService } from '../services/services/user.service';
import { BlogService } from '../services/services/blog.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { of, throwError } from 'rxjs';
import { HttpClientModule } from '@angular/common/http';

describe('SignInComponent', () => {
  let component: SignInComponent;
  let fixture: ComponentFixture<SignInComponent>;
  let userService: UserService;
  let blogService: BlogService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        SignInComponent, BrowserAnimationsModule, HttpClientModule
      ],
      providers: [UserService, BlogService]
    }).compileComponents();

    fixture = TestBed.createComponent(SignInComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    userService = TestBed.inject(UserService);
    blogService = TestBed.inject(BlogService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('form invalid when empty', () => {
    expect(component.userFormValidation.valid).toBeFalsy();
  });

  it('usernameOrEmail field validity', () => {
    let usernameOrEmail = component.userFormValidation.controls['usernameOrEmailValidator'];
    expect(usernameOrEmail.valid).toBeFalsy();
    usernameOrEmail.setValue("");
    expect(usernameOrEmail.hasError('required')).toBeTruthy();
  });

  it('password field validity', () => {
    let password = component.userFormValidation.controls['passwordValidator'];
    expect(password.valid).toBeFalsy();
    password.setValue("");
    expect(password.hasError('required')).toBeTruthy();
  });

  it('should display success snackbar on successful login', () => {
    spyOn(userService, 'login').and.returnValue(of('User Logged In successfully!'));
    spyOn(component['snackBar'], 'open');
    component.userForm.value.usernameOrEmailValidator = 'test@example.com';
    component.userForm.value.passwordValidator = 'password123';
    component.login();
    expect(component['snackBar'].open).toHaveBeenCalledWith('User Logged In successfully!', 'Close', jasmine.any(Object));
  });

  it('should display error snackbar on login failure', () => {
    const errorResponse = new Error('Login failed');
    spyOn(userService, 'login').and.returnValue(throwError(() => errorResponse));
    spyOn(component['snackBar'], 'open');
    component.userForm.value.usernameOrEmailValidator = 'wrong@example.com';
    component.userForm.value.passwordValidator = 'wrongpassword';
    component.login();
    expect(component.snackBar.open).toHaveBeenCalledWith('Login failed', 'Close', jasmine.any(Object));  
  });

  // Additional tests can be added here for other functionalities.
});