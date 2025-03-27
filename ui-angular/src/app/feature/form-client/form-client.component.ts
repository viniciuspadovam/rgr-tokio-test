import { CommonModule } from '@angular/common';
import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, of, tap } from 'rxjs';
import { Client } from '../../core/model/client';
import { ClientService } from '../../core/service/client.service';

@Component({
  selector: 'app-form',
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './form-client.component.html',
  styleUrl: './form-client.component.scss'
})
export class FormClientComponent implements OnInit, OnChanges {
  public client: Client | null = null;
  public form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private clientService: ClientService
  ) { }

  ngOnInit(): void {
    this.client = history.state.client;

    this.initForm();
    if (this.client) this.patchForm(this.client);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['client'] && !changes['client'].isFirstChange()) {
      if (this.client) {
        this.patchForm(this.client);
      } else {
        this.initForm();
      }
    }
  }

  private initForm(): void {
    this.form = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required]
    });
  }

  private patchForm(client: Client): void {
    this.form.patchValue({
      firstName: client.firstName,
      lastName: client.lastName,
      email: client.email
    });
  }

  onSubmit(): void {
    if (this.form.valid ) {
      if(!this.client) {
        this.create();
      } else {
        this.update();
      }
    } else {
      this.form.markAllAsTouched();
    }
  }

  private create(): void {
    this.clientService.create(this.form.value).pipe(
      tap(response => {
        console.log(`Cliente ${response.id} cadastrada com sucesso!`);
        this.router.navigate(['']);
      }),
      catchError(e => of(e))
    ).subscribe();
  }

  private update() {
    this.clientService.update(this.client!.id, this.form.value).pipe(
      tap(response => {
        console.log(`Cliente ${response.id} alterada com sucesso!`);
        this.router.navigate(['']);
      }),
      catchError(e => of(e))
    ).subscribe();
  }

  public back(): void {
    this.router.navigate(['']);
  }

}
