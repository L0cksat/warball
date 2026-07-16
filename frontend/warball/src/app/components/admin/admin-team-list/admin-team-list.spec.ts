import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminTeamList } from './admin-team-list';

describe('AdminTeamList', () => {
  let component: AdminTeamList;
  let fixture: ComponentFixture<AdminTeamList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminTeamList],
    }).compileComponents();

    fixture = TestBed.createComponent(AdminTeamList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
