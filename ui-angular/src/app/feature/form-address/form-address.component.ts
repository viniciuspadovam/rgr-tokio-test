import { CommonModule } from '@angular/common';
import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Address } from '../../core/model/address';
import { AddressService } from '../../core/service/address.service';
import { catchError, of, tap } from 'rxjs';

@Component({
  selector: 'app-form',
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './form-address.component.html',
  styleUrl: './form-address.component.scss'
})
export class FormAddressComponent implements OnInit, OnChanges {
  private clientId!: number;
  public address: Address | null = null;
  public form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private addressService: AddressService
  ) { }

  ngOnInit(): void {
    this.clientId = history.state.clientId;
    this.address = history.state.address;
    this.initForm();
    if (this.address) this.patchForm(this.address);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['address'] && !changes['address'].isFirstChange()) {
      if (this.address) {
        this.patchForm(this.address);
      } else {
        this.initForm();
      }
    }
  }

  private initForm(): void {
    this.form = this.fb.group({
      postalCode: ['', [Validators.required, Validators.minLength(8)]],
      address: ['', Validators.required],
      complement: ['', Validators.required],
      number: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required]
    });
  }

  private patchForm(address: Address): void {
    this.form.patchValue({
      postalCode: address.postalCode,
      address: address.address,
      complement: address.complement,
      number: address.number,
      city: address.city,
      state: address.state
    });
  }

  buscarCEP(): void {
    const cep = this.form.get('postalCode')?.value;
    if (cep && cep.length === 8) {
      this.form.disable();
      this.addressService.getByCep(cep).pipe(
        tap(data => {
          this.form.patchValue({
            address: data.address,
            complement: data.complement,
            city: data.city,
            state: data.state
          });
          this.form.enable();
        }),
        catchError(error => {
          console.error('Erro ao buscar CEP:', error);
          this.form.enable();
          return of(error);
        })
      ).subscribe();
    }
  }

  onSubmit(): void {
    if (this.form.valid) {
      if (!this.address) {
        this.create();
      } else {
        this.update();
      }
    } else {
      this.form.markAllAsTouched();
    }
  }

  private create(): void {
    this.addressService.create(this.clientId, this.form.value).pipe(
      tap(response => {
        console.log(`Endereço ${response.id} cadastrado com sucesso!`);
        this.router.navigate(['']);
      }),
      catchError(error => {
        console.error('Erro ao cadastrar endereço:', error);
        return of(error);
      })
    ).subscribe();
  }

  private update(): void {
    this.addressService.update(this.address!.id, this.form.value).pipe(
      tap(response => {
        console.log(`Endereço ${response.id} atualizado com sucesso!`);
        this.router.navigate(['']);
      }),
      catchError(error => {
        console.error('Erro ao atualizar endereço:', error);
        return of(error);
      })
    ).subscribe();
  }

  public back(): void {
    this.router.navigate(['']);
  }

}
