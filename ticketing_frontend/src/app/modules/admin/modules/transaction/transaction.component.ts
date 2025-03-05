import {Component, OnDestroy, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {TransactionService} from "./services/transaction.service";
import {first, Subscription} from "rxjs";
import {TransactionModel} from "../../../../models/transaction.model";
import {DatePipe, formatDate} from "@angular/common";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzInputModule} from "ng-zorro-antd/input";
import {NzDatePickerComponent, NzRangePickerComponent} from "ng-zorro-antd/date-picker";
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzCardModule} from "ng-zorro-antd/card";
import {NzBreadCrumbComponent, NzBreadCrumbItemComponent} from "ng-zorro-antd/breadcrumb";
import {NzIconModule} from "ng-zorro-antd/icon";
import {NzTableComponent} from "ng-zorro-antd/table";
import {NzDividerComponent} from "ng-zorro-antd/divider";
import Swal from "sweetalert2";
import {NgbPagination} from "@ng-bootstrap/ng-bootstrap";
import {NzOptionComponent, NzSelectComponent} from "ng-zorro-antd/select";

@Component({
  selector: 'app-transaction',
  standalone: true,
  imports: [
    RouterLink,
    DatePipe,
    FormsModule,
    ReactiveFormsModule,
    NzInputModule,
    NzRangePickerComponent,
    NzDatePickerComponent,
    NzButtonComponent,
    NzCardModule,
    NzBreadCrumbComponent,
    NzBreadCrumbItemComponent,
    NzIconModule,
    NzTableComponent,
    NzDividerComponent,
    NgbPagination,
    NzSelectComponent,
    NzOptionComponent
  ],
  templateUrl: './transaction.component.html',
  styleUrl: './transaction.component.scss'
})
export class TransactionComponent implements OnInit, OnDestroy {
  date: any;
  page = 1;
  pageSize = 10;
  searchValue = '';
  endDate: Date | undefined;
  startDate: Date | undefined;
  transactions: TransactionModel[] = [];
  transactionsPage: TransactionModel[] = [];
  searchFormGroup: FormGroup = new FormGroup({});

  private unsubscribe: Subscription[] = [];

  constructor(
    private fb: FormBuilder, private service: TransactionService) {
  }

  get f() {
    return this.searchFormGroup.controls;
  }

  get collectionSize(): number {
    return this.transactions.length || 0
  }

  ngOnDestroy(): void {
    this.unsubscribe.forEach((sb) => sb.unsubscribe());
  }

  ngOnInit(): void {
    this.initForm();
    this.getTransactions();
  }

  searchData(): void {
    const searchValue = this.f["search"].value
    if (!searchValue) {
      this.getTransactions();
    } else {
      if ((this.startDate && this.endDate)) {
        if (this.startDate < this.endDate) {
          this.getTransactionsBySearchValueAndDate(searchValue, this.startDate, this.endDate);
        }
      } else {
        this.getSearchTransactions(searchValue);
      }
    }
  }

  findByDate($event: any) {
    let startDate: Date = this.startDate = $event[0];
    let endDate: Date = this.endDate = $event[1];
    if (startDate < endDate) {
      if (this.searchValue !== "") {
        this.getTransactionsBySearchValueAndDate(this.searchValue, startDate, endDate);
      } else {
        this.getTransactionsByDate(startDate, endDate);
      }
    } else {
      this.getTransactions();
    }
  }

  checkValidTicket(codeTicket: string): void {
    const sub = this.service.checkTicket(codeTicket).subscribe((response) => {
      if (response) {
        if (response.status) {
          Swal.fire({
            title: 'Valid!',
            text: 'Votre ticket est bien valide.\n Date d\'expiration : ' + formatDate(response.expirationTime, "dd/MM/yyyy", "en"),
            icon: 'success',
            confirmButtonText: 'OK'
          });
        } else {
          Swal.fire({
            title: 'Invalid!',
            text: 'Votre ticket n\'est pas valide.\n Date d\'expiration : ' + formatDate(response.expirationTime, "dd/MM/yyyy", "en"),
            icon: 'error',
            confirmButtonText: 'OK'
          });
        }
      }
    });
    this.unsubscribe.push(sub);
  }

  refreshTransactions(): void {
    this.transactionsPage = this.transactions.slice(
      (this.page - 1) * this.pageSize,
      (this.page - 1) * this.pageSize + this.pageSize,
    );
  }

  private getSearchTransactions(searchValue: string): void {
    const transSub = this.service.searchTransaction(searchValue).pipe(first())
      .subscribe(transactions => {
        if (transactions) {
          this.transactions = transactions;
        }
      });
    this.unsubscribe.push(transSub);
  }

  private getTransactions(): void {
    const transactionSub = this.service.listTransaction().pipe(first())
      .subscribe((transactions) => {
        if (transactions) {
          this.transactions = transactions;
          this.refreshTransactions();
        }
      });
    this.unsubscribe.push(transactionSub);
  }

  private initForm(): void {
    this.searchFormGroup = this.fb.group({
      search: ['']
    });
  }

  private getTransactionsBySearchValueAndDate(searchValue: string, startDate: Date, endDate: Date): void {
    const end = formatDate(endDate, 'yyyy-MM-dd', 'en');
    const start = formatDate(startDate, 'yyyy-MM-dd', 'en');
    const transactionSub = this.service.searchTransactionByDate(searchValue, start, end).pipe(first())
      .subscribe((transactions) => {
        if (transactions) {
          this.transactions = transactions;
        }
      });
    this.unsubscribe.push(transactionSub);
  }

  private getTransactionsByDate(startDate: Date, endDate: Date): void {
    const end = formatDate(endDate, 'yyyy-MM-dd', 'en');
    const start = formatDate(startDate, 'yyyy-MM-dd', 'en');
    const transactionSub = this.service.listTransactionByDate(start, end).pipe(first())
      .subscribe((transactions) => {
        if (transactions) {
          this.transactions = transactions;
        }
      });
    this.unsubscribe.push(transactionSub);
  }
}
