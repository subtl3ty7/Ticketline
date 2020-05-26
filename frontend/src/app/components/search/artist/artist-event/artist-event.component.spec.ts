import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArtistEventComponent } from './artist-event.component';

describe('ArtistEventComponent', () => {
  let component: ArtistEventComponent;
  let fixture: ComponentFixture<ArtistEventComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArtistEventComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtistEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
