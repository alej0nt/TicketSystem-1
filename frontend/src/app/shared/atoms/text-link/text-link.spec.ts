import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TextLinkComponent } from './text-link';

describe('TextLink', () => {
  let component: TextLinkComponent;
  let fixture: ComponentFixture<TextLinkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TextLinkComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TextLinkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
