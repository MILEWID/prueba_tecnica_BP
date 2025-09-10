import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { App } from './app';

describe('App', () => {
  let component: App;
  let fixture: any;
  let mockRouter: jest.Mocked<Router>;

  beforeEach(async () => {
    mockRouter = {
      events: {
        pipe: jest.fn().mockReturnValue({
          subscribe: jest.fn()
        })
      },
      navigate: jest.fn()
    } as any;

    await TestBed.configureTestingModule({
      imports: [App],
      providers: [
        { provide: Router, useValue: mockRouter }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(App);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
