import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Client } from '../model/client';
import { ClientRequest } from '../model/requests';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private readonly baseApiEndpoint: string = 'http://localhost:8080/consulta-usuario-tokio/api/v1/client';

  constructor(private httpClient: HttpClient) {}

  public listAll(): Observable<Client[]> {
    return this.httpClient.get<Client[]>(this.baseApiEndpoint);
  }

  public findByName(name: string): Observable<Client[]> {
    const params = new HttpParams().set('name', name);
    return this.httpClient.get<Client[]>(`${this.baseApiEndpoint}/find-by`, { params });
  }

  public create(client: ClientRequest): Observable<Client> {
    return this.httpClient.post<Client>(this.baseApiEndpoint, client);
  }

  public update(id: number, client: ClientRequest): Observable<Client> {
    return this.httpClient.put<Client>(`${this.baseApiEndpoint}/${id}`, client);
  }

  public delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseApiEndpoint}/${id}`);
  }

}
