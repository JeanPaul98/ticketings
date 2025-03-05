import {Component, inject, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {TransactionModel} from "../../../../models/transaction.model";
import {first, Subscription} from "rxjs";
import {TransactionService} from "../transaction/services/transaction.service";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {getResult} from "../../../../../assets/ts/main";

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrl: './report.component.scss'
})
export class ReportComponent implements OnInit, OnDestroy {
  page = 1;
  pageSize = 10;
  closeResult = '';
  transactions: TransactionModel[] = [];
  transactionsPage: TransactionModel[] = [];
  formGroup: FormGroup = new FormGroup({});

  private unsubscribe: Subscription[] = [];
  private modalService = inject(NgbModal);

  constructor(private service: TransactionService, private fb: FormBuilder) {
  }

  get collectionSize(): number {
    return this.transactions.length || 0
  }

  get f() {
    return this.formGroup.controls;
  }

  ngOnDestroy(): void {
    this.unsubscribe.forEach((sb) => sb.unsubscribe());
  }

  ngOnInit(): void {
    this.initForm();
    this.getTransactions();
  }

  refreshTransactions(): void {
    this.transactionsPage = this.transactions.slice(
      (this.page - 1) * this.pageSize,
      (this.page - 1) * this.pageSize + this.pageSize,
    );
  }

  exportToCsv(): void {
    const data = getResult(this.f);
    this.service.reportTransactionByDate(data).pipe(first())
      .subscribe(response => {
        if (response) {
          this.modalService.dismissAll();
        }
      });
  }

  open(content: TemplateRef<any>) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', size: 'lg'}).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      },
    );
  }

  private getDismissReason(reason: any): string {
    switch (reason) {
      case ModalDismissReasons.ESC:
        return 'by pressing ESC';
      case ModalDismissReasons.BACKDROP_CLICK:
        return 'by clicking on a backdrop';
      default:
        return `with: ${reason}`;
    }
  }

  private getTransactions(): void {
    const transactionSub = this.service.reportTransaction().pipe(first())
      .subscribe((transactions) => {
        if (transactions) {
          this.transactions = transactions;
          this.refreshTransactions();
        }
      });
    this.unsubscribe.push(transactionSub);
  }

  private initForm(): void {
    this.formGroup = this.fb.group({
      startDate: ['', Validators.compose([Validators.required])],
      endDate: ['', Validators.compose([Validators.required])]
    });
  }
}
