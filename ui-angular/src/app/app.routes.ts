import { Routes } from '@angular/router';
import { ListComponent } from './feature/list/list.component';
import { FormClientComponent } from './feature/form-client/form-client.component';
import { FormAddressComponent } from './feature/form-address/form-address.component';

export const routes: Routes = [
    { path: '', component: ListComponent, pathMatch: 'full' },
    { path: 'form-client', component: FormClientComponent },
    { path: 'form-address', component: FormAddressComponent }
];
