import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArtistAdvancedComponent } from './artist-advanced.component';

describe('ArtistAdvancedComponent', () => {
  let component: ArtistAdvancedComponent;
  let fixture: ComponentFixture<ArtistAdvancedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArtistAdvancedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtistAdvancedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
