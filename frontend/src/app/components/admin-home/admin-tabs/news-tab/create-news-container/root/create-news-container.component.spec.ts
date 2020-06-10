import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewsContainerComponent } from './create-news-container.component';

describe('CreateNewsContainerComponent', () => {
  let component: CreateNewsContainerComponent;
  let fixture: ComponentFixture<CreateNewsContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewsContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewsContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
