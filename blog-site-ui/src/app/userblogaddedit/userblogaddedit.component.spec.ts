import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserblogaddeditComponent } from './userblogaddedit.component';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BlogService } from '../services/services/blog.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

describe('UserblogaddeditComponent', () => {
  let component: UserblogaddeditComponent;
  let fixture: ComponentFixture<UserblogaddeditComponent>;
  let blogService: BlogService;
  let dialogRefSpy: jasmine.SpyObj<MatDialogRef<UserblogaddeditComponent>>;

  beforeEach(async () => {
    const dialogMock = {
      close: () => {}
    };

    await TestBed.configureTestingModule({
      imports: [
        UserblogaddeditComponent, HttpClientModule, BrowserAnimationsModule
      ],
      providers: [
        FormBuilder,
        BlogService,
        { provide: MatDialogRef, useValue: dialogMock },
        { provide: MAT_DIALOG_DATA, useValue: {} }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserblogaddeditComponent);
    component = fixture.componentInstance;
    blogService = TestBed.inject(BlogService);
    dialogRefSpy = TestBed.inject(MatDialogRef) as jasmine.SpyObj<MatDialogRef<UserblogaddeditComponent>>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should close dialog on successful form submission', () => {
    spyOn(blogService, 'addBlog').and.returnValue({ subscribe: (callbacks: { next: Function }) => callbacks.next({}) } as any);
    spyOn(dialogRefSpy, 'close');
    component.blogForm.setValue({ blogName: 'Test', blogCategory: 'Test Category', blogAuthor: 'Test Author', blogArticle: 'Test Article' });
    component.onFormSubmit();
    expect(dialogRefSpy.close).toHaveBeenCalledWith(true);
  });

  it('should not close dialog on form submission if form is invalid', () => {
    component.blogForm.setValue({ blogName: '', blogCategory: '', blogAuthor: '', blogArticle: '' }); // Invalid form
    spyOn(dialogRefSpy, 'close');
    component.onFormSubmit();
    expect(dialogRefSpy.close).not.toHaveBeenCalled();
  });
});