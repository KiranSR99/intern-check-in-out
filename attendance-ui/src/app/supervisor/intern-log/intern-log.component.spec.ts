import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternLogComponent } from './intern-log.component';

describe('InternLogComponent', () => {
  let component: InternLogComponent;
  let fixture: ComponentFixture<InternLogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InternLogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InternLogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
