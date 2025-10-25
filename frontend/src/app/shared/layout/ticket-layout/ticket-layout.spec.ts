import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketLayout } from './ticket-layout';

describe('TicketLayout', () => {
  let component: TicketLayout;
  let fixture: ComponentFixture<TicketLayout>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketLayout]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketLayout);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
