import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPlayerForm } from './admin-player-form';

describe('AdminPlayerForm', () => {
  let component: AdminPlayerForm;
  let fixture: ComponentFixture<AdminPlayerForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminPlayerForm],
    }).compileComponents();

    fixture = TestBed.createComponent(AdminPlayerForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
