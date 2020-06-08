import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateArtistsComponent } from './create-artists.component';

describe('CreateArtistsComponent', () => {
  let component: CreateArtistsComponent;
  let fixture: ComponentFixture<CreateArtistsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateArtistsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateArtistsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
