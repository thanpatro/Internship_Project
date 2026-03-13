import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

import { App } from './app';

describe('App', () => {
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    // Stub alert for Node/jsdom environment
    globalThis.alert = () => {};

    await TestBed.configureTestingModule({
      imports: [App, RouterTestingModule],
      providers: [
        provideHttpClientTesting(),
      ],
    }).compileComponents();

    httpMock = TestBed.inject(HttpTestingController);
    httpMock
      .match(req =>
        req.url === 'http://localhost:8080/companies' ||
        req.url === 'http://localhost:8080/employees' ||
        req.url === 'http://localhost:8080/devices'
      )
      .forEach(req => req.flush([]));
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should render title', async () => {
    const fixture = TestBed.createComponent(App);
    await fixture.whenStable();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Inventory');
  });
});
