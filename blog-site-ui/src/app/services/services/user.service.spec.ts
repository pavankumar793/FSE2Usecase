import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user.service';
import { UserRegister, UserLogin } from '../../models/user.model';
import { SERVICE_URI } from '../../../service.uri';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });
    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
    // Mock local storage
    let store: { [key: string]: string } = {};
    spyOn(localStorage, 'getItem').and.callFake((key) => store[key] || null);
    spyOn(localStorage, 'setItem').and.callFake((key, value) => store[key] = value + '');
    spyOn(localStorage, 'removeItem').and.callFake((key) => delete store[key]);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should register a new user', () => {
    const mockResponse = 'User registered successfully';
    const data: UserRegister = new UserRegister();
    service.register(data).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });
    const req = httpMock.expectOne(SERVICE_URI.register);
    expect(req.request.method).toBe('POST');
    req.flush(mockResponse);
  });

  it('should login a user and store in local storage', () => {
    const mockUser = "User logged in successfully";
    const data: UserLogin = new UserLogin();
    service.login(data).subscribe(response => {
      expect(response).toEqual(mockUser);
      expect(localStorage.setItem).toHaveBeenCalledWith('currentUser', JSON.stringify(mockUser));
    });
    const req = httpMock.expectOne(SERVICE_URI.login);
    expect(req.request.method).toBe('POST');
    req.flush(mockUser);
  });

  it('should handle login error', () => {
    const errorMessage = 'Incorrect username or email';
    const data: UserLogin = new UserLogin();
    service.login(data).subscribe({
      next: () => {},
      error: (error) => {
        expect(error.message).toContain(errorMessage);
      }
    });
    const req = httpMock.expectOne(SERVICE_URI.login);
    req.flush({ error: errorMessage }, { status: 400, statusText: 'Bad Request' });
  });

  // Additional tests can be added here for other functionalities and error handling scenarios.
});