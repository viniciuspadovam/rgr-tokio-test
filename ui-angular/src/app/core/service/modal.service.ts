import { Injectable, Type } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ModalSubject } from '../model/modal-subject';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  private modalSubject: BehaviorSubject<ModalSubject | null> = new BehaviorSubject<ModalSubject | null>(null);
  public modalState$: Observable<ModalSubject | null> = this.modalSubject.asObservable();

  public open(component: Type<any>, data?: any): void {
    this.modalSubject.next({ component, data });
  }

  public close(): void {
    this.modalSubject.next(null);
  }
  
}
