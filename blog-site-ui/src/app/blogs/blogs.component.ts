import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FormsModule, NgForm, ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule, MatOptionModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BlogService } from '../services/services/blog.service';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { Blog } from '../models/blog.model';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-blogs',
  standalone: true,
  imports: [MatToolbarModule, CommonModule, MatSelectModule, FormsModule, ReactiveFormsModule, MatDatepickerModule, MatFormFieldModule, MatOptionModule, MatInputModule, MatNativeDateModule, MatTableModule, MatPaginatorModule, MatIconModule, MatButtonModule],
  templateUrl: './blogs.component.html',
  styleUrl: './blogs.component.css'
})
export class BlogsComponent implements AfterViewInit {
  hideMain: boolean = false;
  userInfo: boolean = false;
  userInfoName: string = "";
  blogs: Blog[] = [];
  displayedColumns: string[] = ['id', 'name', 'category', 'article', 'author', 'createdOn'];
  dataSource = new MatTableDataSource<Blog>(this.blogs);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild('searchForm') searchForm!: NgForm;
  searchFormValidation!: FormGroup;
  categories = [
    { value: 'Action', viewValue: 'Action' },
    { value: 'Anime', viewValue: 'Anime' },
    { value: 'Horror', viewValue: 'Horror' },
    { value: 'Thriller', viewValue: 'Thriller' },
    { value: 'Comedy', viewValue: 'Comedy' },
    { value: 'Crime', viewValue: 'Crime' }
  ];

  constructor(private fb: FormBuilder, private blogService: BlogService, private router: Router) {
    this.searchFormValidation = this.fb.group({
      categoryValidator: ['', Validators.required],
      fromDateValidator: [''],
      toDateValidator: ['']
    });
  }

  ngOnInit() {
    this.hideMain = false;
    if (this.blogService.getUserName() != "") {
      this.userInfo = true;
      this.userInfoName = this.blogService.getUserName();
    }
  }

  onSubmit() {
    if (this.searchForm.value.fromDateValidator == "" || this.searchForm.value.toDateValidator == "") {
      this.blogService.searchByCategory(this.searchForm.value.categoryValidator).subscribe({
        next: (message) => {
          this.blogs = JSON.parse(message);
          this.dataSource = new MatTableDataSource<Blog>(this.blogs == null ? [] : this.blogs);
          this.dataSource.paginator = this.paginator;
        },
        error: (error) => {
          console.error('Search failed:', error);
        }
      });
    } else {
      this.blogService.searchByCategoryAndFromToDates(this.searchForm.value.categoryValidator, new Intl.DateTimeFormat('en-CA', { year: 'numeric', month: '2-digit', day: '2-digit' }).format(this.searchForm.value.fromDateValidator), new Intl.DateTimeFormat('en-CA', { year: 'numeric', month: '2-digit', day: '2-digit' }).format(this.searchForm.value.toDateValidator)).subscribe({
        next: (message) => {
          this.blogs = JSON.parse(message);
          this.dataSource = new MatTableDataSource<Blog>(this.blogs == null ? [] : this.blogs);
          this.dataSource.paginator = this.paginator;
        },
        error: (error) => {
          console.error('Search failed:', error);
        }
      });
    }
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  onHomeIconClick() {
    this.hideMain = false;
    this.router.navigate(['/']);
  }
}
