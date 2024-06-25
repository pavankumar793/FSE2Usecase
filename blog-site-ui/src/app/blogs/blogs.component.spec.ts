import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { BlogsComponent } from './blogs.component';
import { BlogService } from '../services/services/blog.service';
import { Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatPaginatorModule } from '@angular/material/paginator';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';
import { of, throwError } from 'rxjs';

describe('BlogsComponent', () => {
  let component: BlogsComponent;
  let fixture: ComponentFixture<BlogsComponent>;
  let blogServiceMock: any;
  let routerMock: any;

  beforeEach(async () => {
    blogServiceMock = jasmine.createSpyObj('BlogService', ['searchByCategory', 'searchByCategoryAndFromToDates']);
    routerMock = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [BlogsComponent, BrowserAnimationsModule],
      providers: [
        { provide: BlogService, useValue: blogServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(BlogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should set hideMain to false on ngOnInit', fakeAsync(() => {
    // Simulate time passage if necessary
    tick();
    fixture.detectChanges(); // Trigger change detection after async operations
    expect(component.hideMain).toBeFalse();
  }));

  it('should handle form submission with only category', () => {
    const mockResponse = '[]'; // Assuming the service returns an empty array
    blogServiceMock.searchByCategory.and.returnValue(of(mockResponse));

    component.searchForm.value.categoryValidator = 'Action';
    component.searchForm.value.fromDateValidator = "";
    component.searchForm.value.toDateValidator = "";
    component.onSubmit();

    expect(blogServiceMock.searchByCategory).toHaveBeenCalledWith('Action');
    expect(component.blogs).toEqual([]);
  });

  it('should handle form submission with category and date range', () => {
    const mockResponse = '[]'; // Assuming the service returns an empty array
    blogServiceMock.searchByCategoryAndFromToDates.and.returnValue(of(mockResponse));

    component.searchForm.value.categoryValidator = 'Action';
    component.searchForm.value.fromDateValidator = new Date();
    component.searchForm.value.toDateValidator = new Date();
    component.onSubmit();

    expect(blogServiceMock.searchByCategoryAndFromToDates).toHaveBeenCalled();
    expect(component.blogs).toEqual([]);
  });

  it('should handle errors during form submission', () => {
    blogServiceMock.searchByCategory.and.returnValue(throwError(() => new Error('Error')));
    
    component.searchForm.value.categoryValidator = 'Action';
    component.searchForm.value.fromDateValidator = "";
    component.searchForm.value.toDateValidator = "";
    component.onSubmit();

    expect(component.blogs).toEqual([]);
    // Additional expectations can be added to check UI feedback for errors
  });

  it('should assign paginator after view init', () => {
    component.ngAfterViewInit();
    expect(component.dataSource.paginator).toBeTruthy();
  });
});