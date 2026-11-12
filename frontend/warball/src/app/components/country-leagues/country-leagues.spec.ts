import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CountryLeagues } from './country-leagues';

describe('CountryLeagues', () => {
  let component: CountryLeagues;
  let fixture: ComponentFixture<CountryLeagues>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CountryLeagues],
    }).compileComponents();

    fixture = TestBed.createComponent(CountryLeagues);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
