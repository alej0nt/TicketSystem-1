import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputFieldShowButtonComponent } from './input-field-showbutton';

describe('InputFieldShowbutton', () => {
  let component: InputFieldShowButtonComponent;
  let fixture: ComponentFixture<InputFieldShowButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InputFieldShowButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InputFieldShowButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
