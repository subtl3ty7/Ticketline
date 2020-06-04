import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewsFormComponent } from './create-news-form.component';

describe('CreateNewsFormComponent', () => {
  let component: CreateNewsFormComponent;
  let fixture: ComponentFixture<CreateNewsFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewsFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
