import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { BlogService } from './blog.service';
import { SERVICE_URI } from '../../../service.uri';
import { BlogRequest } from '../../models/blog.model';

describe('BlogService', () => {
  let service: BlogService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [BlogService]
    });
    service = TestBed.inject(BlogService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should set and get the username', () => {
    const testUsername = 'testUser';
    service.setUserName(testUsername);
    expect(service.getUserName()).toEqual(testUsername);
  });

  it('should search blogs by category', () => {
    const testCategory = 'testCategory';
    service.searchByCategory(testCategory).subscribe();
    const req = httpMock.expectOne(`${SERVICE_URI.searchByCategory}${testCategory}`);
    expect(req.request.method).toBe('GET');
  });

  it('should search blogs by category and date range', () => {
    const category = 'testCategory';
    const fromDate = '2023-01-01';
    const toDate = '2023-01-31';
    service.searchByCategoryAndFromToDates(category, fromDate, toDate).subscribe();
    const req = httpMock.expectOne(`${SERVICE_URI.searchByCategory}${category}/${fromDate}/${toDate}`);
    expect(req.request.method).toBe('GET');
  });

  it('should get blogs by user', () => {
    service.getBlogsByUser().subscribe();
    const req = httpMock.expectOne(`${SERVICE_URI.getBlogsByUser}${service.getUserName()}`);
    expect(req.request.method).toBe('GET');
  });

  it('should add a blog', () => {
    const blogReq = new BlogRequest();
    service.addBlog(blogReq).subscribe();
    const req = httpMock.expectOne(SERVICE_URI.addBlog);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(blogReq);
  });

  it('should delete a blog', () => {
    const blogId = 'testBlogId';
    service.deleteBlog(blogId).subscribe();
    const req = httpMock.expectOne(`${SERVICE_URI.deleteBlog}${blogId}`);
    expect(req.request.method).toBe('DELETE');
  });

  // Additional tests can be added here for error handling, etc.
});