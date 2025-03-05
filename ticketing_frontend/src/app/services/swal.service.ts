import {Injectable} from "@angular/core";
import Swal, {SweetAlertResult} from "sweetalert2";

@Injectable({
  providedIn: "root"
})
export class SwalService {

  toastSuccess(text: string): Promise<SweetAlertResult> {
    return this.toastMixin().fire({
      icon: "success",
      text: text
    });
  }

  toastError(text: string): Promise<SweetAlertResult> {
    return this.toastMixin().fire({
      icon: "error",
      text: text
    });
  }

  toastErrorValidation(title: string, description: string): Promise<SweetAlertResult> {
    return this.toastMixin().fire({
      icon: "error",
      title: title,
      text: description
    });
  }

  toastWarning(text: string): Promise<SweetAlertResult> {
    return this.toastMixin().fire({
      icon: "warning",
      text: text
    });
  }

  toastInfo(text: string): Promise<SweetAlertResult> {
    return this.toastMixin().fire({
      icon: "info",
      text: text
    });
  }

  private toastMixin() {
    return Swal.mixin({
      toast: true,
      position: "top-end",
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true,
      didOpen: (toast) => {
        toast.addEventListener("mouseenter", Swal.stopTimer);
        toast.addEventListener("mouseleave", Swal.resumeTimer);
      }
    });
  }
}
