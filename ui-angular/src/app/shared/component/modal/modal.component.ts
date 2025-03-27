import { AfterViewInit, Component, ComponentRef, OnDestroy, ViewChild, ViewContainerRef } from '@angular/core';
import { ModalService } from '../../../core/service/modal.service';

@Component({
  selector: 'app-modal',
  imports: [],
  templateUrl: './modal.component.html',
  styleUrl: './modal.component.scss'
})
export class ModalComponent implements AfterViewInit, OnDestroy {

  private componentRef!: ComponentRef<any>;
  
  @ViewChild('modalContainer', { read: ViewContainerRef, static: false })
  public container!: ViewContainerRef;
  public isVisible: boolean = false;

  constructor(private modalService: ModalService) {}

  ngAfterViewInit() {
    this.modalService.modalState$.subscribe(state => {
      if(state) {
        setTimeout(() => {
          this.loadComponent(state.component, state.data);
        }, 5);
        this.isVisible = true;
      } else {
        this.closeModal();
      }
    });
  }

  private loadComponent(component: any, data?: any): void {
    this.container.clear();
    this.componentRef = this.container.createComponent(component);

    if(data) Object.assign(this.componentRef.instance, data);

    if(this.componentRef.instance.closeModal) {
      this.componentRef.instance.closeModal.subscribe(() => this.closeModal());
    }
  }

  ngOnDestroy() {
    this.closeModal();
  }

  private closeModal() {
    this.isVisible = false;
    if(this.componentRef) this.componentRef.destroy();
  }

}
