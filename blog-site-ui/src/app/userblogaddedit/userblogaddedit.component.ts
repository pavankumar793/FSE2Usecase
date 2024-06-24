import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { BlogService } from '../services/services/blog.service';
import { Blog, BlogRequest } from '../models/blog.model';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-userblogaddedit',
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, MatButtonModule, CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './userblogaddedit.component.html',
  styleUrl: './userblogaddedit.component.css'
})
export class UserblogaddeditComponent {
  blogForm!: FormGroup;
  blogRequest:BlogRequest = new BlogRequest();
  constructor(
    private _fb: FormBuilder,
    private blogService: BlogService,
    private _dialogRef: MatDialogRef<UserblogaddeditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.blogForm = this._fb.group({
      blogName: '',
      blogCategory: '',
      blogAuthor: '',
      blogArticle: ''
    });
  }

  ngOnInit(): void {
    this.blogForm.patchValue(this.data);
    if (this.data.blogId) {
      this.blogRequest.blogId = this.data.blogId;
    }
  }

  onFormSubmit() {
    if (this.blogForm.valid) {
      this.blogRequest.blogName = this.blogForm.value.blogName;
      this.blogRequest.blogCategory = this.blogForm.value.blogCategory;
      this.blogRequest.blogArticle = this.blogForm.value.blogArticle;
      this.blogRequest.blogAuthor = this.blogForm.value.blogAuthor;
      this.blogRequest.userId = this.blogService.getUserName();
      this.blogService
        .addBlog(this.blogRequest)
        .subscribe({
          next: (val: any) => {
            
            this._dialogRef.close(true);
          },
          error: (err: any) => {
            console.error(err);
          },
        });
    }
  }
}
