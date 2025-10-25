import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketPageComponent } from './ticket-page';

describe('TicketPage', () => {
  let component: TicketPageComponent;
  let fixture: ComponentFixture<TicketPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
