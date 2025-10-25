import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketMetaInfo } from './ticket-meta-info';

describe('TicketMetaInfo', () => {
  let component: TicketMetaInfo;
  let fixture: ComponentFixture<TicketMetaInfo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketMetaInfo]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketMetaInfo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
