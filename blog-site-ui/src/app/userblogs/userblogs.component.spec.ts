import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserblogsComponent } from './userblogs.component';

describe('UserblogsComponent', () => {
  let component: UserblogsComponent;
  let fixture: ComponentFixture<UserblogsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserblogsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserblogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
