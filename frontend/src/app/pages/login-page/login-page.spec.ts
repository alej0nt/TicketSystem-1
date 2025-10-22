import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginPageComponent } from './login-page';
import { LoginLayoutComponent } from '../../shared/layout/login-layout/login-layout';

describe('LoginPageComponent', () => {
  let component: LoginPageComponent;
  let fixture: ComponentFixture<LoginPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginPageComponent, LoginLayoutComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should handle login attempt', () => {
    spyOn(console, 'log');
    const credentials = { email: 'test@example.com', password: 'password123' };
    component.handleLoginAttempt(credentials);
    expect(console.log).toHaveBeenCalledWith('Login attempt:', {
      email: 'test@example.com'
    });
  });

  it('should handle register click', () => {
    spyOn(console, 'log');
    component.handleRegisterClick();
    expect(console.log).toHaveBeenCalledWith('Register clicked');
  });
});
