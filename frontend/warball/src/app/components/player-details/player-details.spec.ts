import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayerDetails } from './player-details';

describe('PlayerDetails', () => {
  let component: PlayerDetails;
  let fixture: ComponentFixture<PlayerDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlayerDetails],
    }).compileComponents();

    fixture = TestBed.createComponent(PlayerDetails);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
