import { Routes } from '@angular/router';
import { BlogsComponent } from './blogs/blogs.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { UserblogsComponent } from './userblogs/userblogs.component';
import { AppComponent } from './app.component';

export const routes: Routes = [
  { path: 'blogs', component: BlogsComponent },
  { path: 'signin', component: SignInComponent },
  { path: 'signup', component: SignUpComponent },
  { path: 'userblogs', component: UserblogsComponent },
  { path: '', component: AppComponent }
];
