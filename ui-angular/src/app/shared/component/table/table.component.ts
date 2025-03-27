import { Component, Input } from '@angular/core';
import { LucideAngularModule, Pencil, Plus, Trash2 } from 'lucide-angular';
import { Client } from '../../../core/model/client';
import { ModalService } from '../../../core/service/modal.service';
import { ModalAddressComponent } from '../modal-address/modal-address.component';
import { Address } from '../../../core/model/address';
import { Router } from '@angular/router';
import { ModalDeleteComponent } from '../modal-delete/modal-delete.component';

@Component({
  selector: 'app-table',
  imports: [LucideAngularModule],
  templateUrl: './table.component.html',
  styleUrl: './table.component.scss'
})
export class TableComponent {

  @Input()
  public clients: Client[] = [];
  public readonly Pencil = Pencil;
  public readonly Trash2 = Trash2;
  public readonly Plus = Plus;

  constructor(private modalService: ModalService, private router: Router) {}

  public getFullName(client: Client) {
    return `${client.firstName}  ${client.lastName}`;
  }

  public updateClientForm(client: Client) {
    this.router.navigate(['/form-client'], { state: { client } });
  }

  public openDeleteClientModal(client: Client): void {
    this.modalService.open(ModalDeleteComponent, { params: { data: client, fullName: this.getFullName(client) } });
  }

  public openAddressModal(client: Client): void {
    this.modalService.open(ModalAddressComponent, { params: { address: client.address, fullName: this.getFullName(client) } });
  }

  public createAddressForm(clientId: number) {
    this.router.navigate(['/form-address'], { state: { clientId } });
  }

}
