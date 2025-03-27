import { Component, EventEmitter, Input, Output } from '@angular/core';
import { LucideAngularModule, X } from 'lucide-angular';
import { catchError, of, tap } from 'rxjs';
import { Address } from '../../../core/model/address';
import { Client } from '../../../core/model/client';
import { AddressService } from '../../../core/service/address.service';
import { ClientService } from '../../../core/service/client.service';

interface ModalDeleteParams {
  fullName: string;
  data: Client | Address;
}

@Component({
  selector: 'app-modal-delete',
  imports: [LucideAngularModule],
  templateUrl: './modal-delete.component.html',
  styleUrl: './modal-delete.component.scss'
})
export class ModalDeleteComponent {

  @Input()
  public params!: ModalDeleteParams;

  @Output()
  public closeModal: EventEmitter<void> = new EventEmitter();

  public readonly X = X;

  constructor(
    private clientService: ClientService,
    private addressService: AddressService
  ) {}

  get isClient(): boolean {
    return 'firstName' in this.params.data &&
      'lastName' in this.params.data &&
      'email' in this.params.data;
  }

  public delete() {
    if(this.isClient) this.deleteClient();
    else this.deleteAddress();

    this.close();
  }

  private deleteClient() {
    this.clientService.delete(this.params.data.id).pipe(
      tap(() => {
        console.log(`Cliente ${this.params.data.id} excluído.`);
        window.location.reload();
        this.close();
      }),
      catchError(error => of(error))
    ).subscribe();
  }

  private deleteAddress() {
    this.addressService.delete(this.params.data.id).pipe(
      tap(() => {
        console.log(`Endereço ${this.params.data.id} excluído.`);
        window.location.reload();
        this.close();
      }),
      catchError(error => of(error))
    ).subscribe();
  }

  public close() {
    this.closeModal.emit();
  }

}
