import { Component, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute, Router } from '@angular/router';
import { BlogService } from '../services/services/blog.service';
import { MatDialog } from '@angular/material/dialog';
import { Blog } from '../models/blog.model';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSortModule } from '@angular/material/sort';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { UserblogaddeditComponent } from '../userblogaddedit/userblogaddedit.component';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { UserService } from '../services/services/user.service';

@Component({
  selector: 'app-userblogs',
  standalone: true,
  imports: [MatSnackBarModule, CommonModule, MatSelectModule, ReactiveFormsModule , MatInputModule, FormsModule, MatTableModule, MatIconModule, MatButtonModule, MatSortModule, MatToolbarModule, MatFormFieldModule, MatPaginatorModule],
  templateUrl: './userblogs.component.html',
  styleUrl: './userblogs.component.css'
})
export class UserblogsComponent {
  blogs: Blog[] = [];
  hideMain: boolean = false;
  displayedColumns: string[] = ['id', 'name', 'category', 'article', 'author', 'createdOn', 'action'];
  dataSource = new MatTableDataSource<Blog>(this.blogs);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private router:Router, private route: ActivatedRoute, private _dialog: MatDialog, private blogService: BlogService, private snackBar:MatSnackBar, private userService:UserService) { }

  ngOnInit(): void {
    this.hideMain = false;
    this.getBlogsList();
  }

  getBlogsList() {
    this.blogService.getBlogsByUser().subscribe({
      next: (res:Blog[]) => {
        this.blogs = res;
        this.dataSource = new MatTableDataSource<Blog>(this.blogs == null ? [] : this.blogs);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: (error) => {
        console.error('Error getting blogs:', error);
      }
    });
  }

  openAddEditForm() {
    const dialogRef = this._dialog.open(UserblogaddeditComponent);
    dialogRef.afterClosed().subscribe({
      next: (val) => {
        if (val) {
          this.getBlogsList();
          this.snackBar.open('Blog added successfully!', 'Close', {
            duration: 3000,
            panelClass: ['success-snackbar']
          });
        }
      },
    });
  }

  openEditForm(data: any) {
    const dialogRef = this._dialog.open(UserblogaddeditComponent, {
      data,
    });

    dialogRef.afterClosed().subscribe({
      next: (val) => {
        if (val) {
          this.getBlogsList();
          this.snackBar.open('Blog updated successfully!', 'Close', {
            duration: 3000,
            panelClass: ['success-snackbar']
          });
        }
      },
    });
  }

  deleteBlog(id: string) {
    this.blogService.deleteBlog(id).subscribe({
      next: (res) => {
        this.getBlogsList();
        this.snackBar.open('Blog delted successfully!', 'Close', {
          duration: 3000,
          panelClass: ['success-snackbar']
        });
      },
      error: console.log,
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  logOut() {
    localStorage.removeItem('currentUser');
    this.router.navigate(['/home']);
    this.snackBar.open("You have been successfully logged out", 'Close', {
      duration: 3000,
      panelClass: ['error-snackbar']
    });
  }

  onHomeIconClick() {
    this.hideMain = false;
    this.router.navigate(['/home']);
  }
}
