import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPlayerList } from './admin-player-list';

describe('AdminPlayerList', () => {
  let component: AdminPlayerList;
  let fixture: ComponentFixture<AdminPlayerList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminPlayerList],
    }).compileComponents();

    fixture = TestBed.createComponent(AdminPlayerList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
