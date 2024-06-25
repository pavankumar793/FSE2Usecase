import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { RouterTestingModule } from '@angular/router/testing';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { UserService } from './services/services/user.service';
import { Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let userServiceStub: Partial<UserService>;
  let router: Router;

  beforeEach(async () => {
    userServiceStub = {
      // Mock userService methods if any used in component
    };

    await TestBed.configureTestingModule({
      imports: [
        AppComponent,
        RouterTestingModule,
        MatSnackBarModule,
        HttpClientModule,
        BrowserAnimationsModule
      ],
      providers: [{ provide: UserService, useValue: userServiceStub }]
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('navigateToSignUp should set hideMain to true and navigate to /signup', () => {
    const navigateSpy = spyOn(router, 'navigate');
    component.navigateToSignUp();
    expect(component.hideMain).toBeTrue();
    expect(navigateSpy).toHaveBeenCalledWith(['/signup']);
  });

  it('navigateToSignIn should set hideMain to true and navigate to /signin', () => {
    const navigateSpy = spyOn(router, 'navigate');
    component.navigateToSignIn();
    expect(component.hideMain).toBeTrue();
    expect(navigateSpy).toHaveBeenCalledWith(['/signin']);
  });

  it('navigateToBlogs should set hideMain to true and navigate to /blogs', () => {
    const navigateSpy = spyOn(router, 'navigate');
    component.navigateToBlogs();
    expect(component.hideMain).toBeTrue();
    expect(navigateSpy).toHaveBeenCalledWith(['/blogs']);
  });
});