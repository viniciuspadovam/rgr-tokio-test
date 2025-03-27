import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ClientService } from '../../core/service/client.service';
import { TableComponent } from "../../shared/component/table/table.component";
import { catchError, of, tap } from 'rxjs';
import { Client } from '../../core/model/client';

@Component({
  selector: 'app-list',
  imports: [CommonModule, FormsModule, TableComponent],
  templateUrl: './list.component.html',
  styleUrl: './list.component.scss'
})
export class ListComponent {

  public clients: Client[] = [];
  public search!: string;

  constructor(private router: Router, private clientService: ClientService) {
      this.clientService.listAll().pipe(
        tap(response => this.clients = response),
        catchError(e => of(e))
      ).subscribe();
  }

  public findClientByName(): void {
    this.clientService.findByName(this.search).pipe(
      tap(response => this.clients = response),
      catchError(e => of(e))
    ).subscribe();
  }
  
  public createClientForm(): void {
    this.router.navigate(['/form-client']);
  }

}
