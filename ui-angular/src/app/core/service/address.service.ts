import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Address } from '../model/address';
import { Observable } from 'rxjs';
import { AddressRequest } from '../model/requests';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private readonly baseApiEndpoint: string = 'http://localhost:8080/consulta-usuario-tokio/api/v1/address';

  constructor(private httpClient: HttpClient) {}

  public create(clientId: number, addressRequest: AddressRequest): Observable<Address> {
      return this.httpClient.post<Address>(`${this.baseApiEndpoint}/${clientId}`, addressRequest);
  }

  public update(id: number, addressRequest: AddressRequest): Observable<Address> {
      return this.httpClient.put<Address>(`${this.baseApiEndpoint}/${id}`, addressRequest);
  }

  public delete(clientId: number): Observable<void> {
      return this.httpClient.delete<void>(`${this.baseApiEndpoint}/${clientId}`);
  }

  public getByCep(cep: string): Observable<Address> {
    const params = new HttpParams().set('cep', cep);
    return this.httpClient.get<Address>(this.baseApiEndpoint, { params });
  }

}
