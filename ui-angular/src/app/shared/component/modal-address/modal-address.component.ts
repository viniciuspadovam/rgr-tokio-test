import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { LucideAngularModule, X } from 'lucide-angular';
import { Address } from '../../../core/model/address';
import { ModalService } from '../../../core/service/modal.service';
import { ModalDeleteComponent } from '../modal-delete/modal-delete.component';

interface ModalAddressParams {
  address: Address;
  fullName: string;
}

@Component({
  selector: 'app-modal-address',
  imports: [LucideAngularModule],
  templateUrl: './modal-address.component.html',
  styleUrl: './modal-address.component.scss'
})
export class ModalAddressComponent {

  @Input()
  public params!: ModalAddressParams;

  @Output()
  public closeModal: EventEmitter<void> = new EventEmitter();

  public readonly X = X;

  constructor(private router: Router, private modalService: ModalService) {}

  public updateAddressForm() {
    this.router.navigate(['/form-address'], { state: { address: this.params.address } });
    this.close();
  }

  public openDeleteAddressModal() {
    this.close();
    this.modalService.open(ModalDeleteComponent, { params: { data: this.params.address, fullName: this.params.fullName } });
  }

  public close() {
    this.closeModal.emit();
  }

}
