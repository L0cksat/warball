import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminTeamForm } from './admin-team-form';

describe('AdminTeamForm', () => {
  let component: AdminTeamForm;
  let fixture: ComponentFixture<AdminTeamForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminTeamForm],
    }).compileComponents();

    fixture = TestBed.createComponent(AdminTeamForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
