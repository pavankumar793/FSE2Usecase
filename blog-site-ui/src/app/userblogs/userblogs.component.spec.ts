import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserblogsComponent } from './userblogs.component';
import { BlogService } from '../services/services/blog.service';
import { of, throwError } from 'rxjs';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Blog } from '../models/blog.model';
import { RouterModule } from '@angular/router';
import { ActivatedRoute } from '@angular/router';

describe('UserblogsComponent', () => {
  let component: UserblogsComponent;
  let fixture: ComponentFixture<UserblogsComponent>;
  let blogService: BlogService;

  // Mock ActivatedRoute with necessary properties
  const activatedRouteMock = {
    // Example: If your component uses paramMap, snapshot, or queryParams
    // Adjust this mock according to your component's usage
    snapshot: {
      paramMap: {
        get: (key: string) => 'someValue', // Mock return value for paramMap.get()
      },
    },
    // If queryParams or other observables are used, mock them as well
    queryParams: of({ key: 'value' }), // Example for queryParams
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ UserblogsComponent, HttpClientModule, BrowserAnimationsModule, RouterModule ],
      providers: [BlogService,
        { provide: ActivatedRoute, useValue: activatedRouteMock }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserblogsComponent);
    component = fixture.componentInstance;
    blogService = TestBed.inject(BlogService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getBlogsList and return list of blogs', () => {
    const response = [new Blog()];
    spyOn(blogService, 'getBlogsByUser').and.returnValue(of(response));
    component.getBlogsList();
    expect(component.blogs.length).toBe(1);
    expect(component.blogs).toEqual(response);
  });

  it('should handle error on getBlogsList failure', () => {
    const error = 'Error getting blogs';
    spyOn(blogService, 'getBlogsByUser').and.returnValue(throwError(() => new Error(error)));
    spyOn(console, 'error');
    component.getBlogsList();
    expect(console.error).toHaveBeenCalledWith('Error getting blogs:', jasmine.any(Error));
  });

  it('should delete a blog and refresh the list', () => {
    spyOn(blogService, 'deleteBlog').and.returnValue(of(""));
    spyOn(component, 'getBlogsList');
    component.deleteBlog('1');
    expect(component.getBlogsList).toHaveBeenCalled();
  });

  it('should handle error on deleteBlog failure', () => {
    const error = 'Error deleting blog';
    spyOn(blogService, 'deleteBlog').and.returnValue(throwError(() => new Error(error)));
    spyOn(console, 'log');
    component.deleteBlog('1');
    expect(console.log).toHaveBeenCalledWith(jasmine.any(Error));
  });

  it('should filter blogs based on user input', () => {
    const event = { target: { value: 'test' } } as unknown as Event;
    component.applyFilter(event);
    expect(component.dataSource.filter).toBe('test');
  });
});