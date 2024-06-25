import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SignUpComponent } from './sign-up.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UserService } from '../services/services/user.service';

describe('SignUpComponent', () => {
  let component: SignUpComponent;
  let fixture: ComponentFixture<SignUpComponent>;
  let mockUserService: any;
  let mockSnackBar: any;
  let mockRouter: any;

  beforeEach(async () => {
    mockUserService = jasmine.createSpyObj('UserService', ['register']);
    mockSnackBar = jasmine.createSpyObj('MatSnackBar', ['open']);
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [ SignUpComponent, HttpClientModule, BrowserAnimationsModule ],
      providers: [
        { provide: UserService, useValue: mockUserService },
        { provide: MatSnackBar, useValue: mockSnackBar },
        { provide: Router, useValue: mockRouter }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(SignUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should register user successfully', () => {
    mockUserService.register.and.returnValue(of('User registered successfully!'));
    component.register();
    expect(mockUserService.register).toHaveBeenCalled();
  });

  it('should handle registration error', () => {
    mockUserService.register.and.returnValue(throwError(() => new Error('Registration failed')));
    component.register();
    expect(mockUserService.register).toHaveBeenCalled();
  });

  it('should navigate to signin on successful registration', () => {
    mockUserService.register.and.returnValue(of('User registered successfully!'));
    component.register();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/signin']);
  });
});