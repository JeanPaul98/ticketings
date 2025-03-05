/**
 * Easy selector helper function
 */

import {AbstractControl, ɵTypedOrUntyped} from "@angular/forms";

export const select = (el: any, all = false) => {
  el = el.trim();
  if (all) {
    return [{...document.querySelectorAll(el)}];
  } else {
    return document.querySelector(el);
  }
};

/**
 * Easy event listener function
 */
export const on = (type: any, el: any, listener: any, all = false) => {
  if (all) {
    select(el, all).forEach((e: Element) => e.addEventListener(type, listener));
  } else {
    select(el, all).addEventListener(type, listener);
  }
};

/**
 * Easy on scroll event listener
 */
export const onscroll = (el: Element | Document, listener: EventListenerOrEventListenerObject) => {
  el.addEventListener("scroll", listener);
};

/**
 * Scrolls to an element with header offset
 */
export const scrollTo = (el: any) => {
  const header = select("#header");
  let offset = header.offsetHeight;

  if (!header.classList.contains("header-scrolled")) {
    offset -= 10;
  }

  const elementPos = select(el).offsetTop;
  window.scrollTo({
    top: elementPos - offset,
    behavior: "smooth"
  });
};

export function getResult(f: ɵTypedOrUntyped<any, any, { [p: string]: AbstractControl }>): {
  [p: string]: string
} {
  const result: {
    [key: string]: string;
  } = {};
  Object.keys(f).forEach((key: string): void => {
    result[key] = f[key].value;
  });
  return result;
}

export function getFormEncodedData(data: { [p: string]: string }): URLSearchParams {
  const formEncoded: URLSearchParams = new URLSearchParams();
  Object.keys(data).forEach((key: string) => {
    formEncoded.set(key, data[key]);
  });
  return formEncoded;
}

export function getDateToTimeStamp(timestamp: number): string {
  const date: Date = new Date(timestamp);
  return `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`;
}

export function getDateTimeToTimeStamp(timestamp: number): Date {
  return new Date(timestamp);
}

export function formatToMoney(amount: number): string {
  const formatter = new Intl.NumberFormat('fr-FR', {
    style: 'currency',
    currency: 'XOF',
  });
  return formatter.format(amount)
}

export function getDownloadPDF(data: Blob, startDate: string, endDate: string): Blob {
  const file = new Blob([data], {type: 'application/pdf'});
  const fileURL = URL.createObjectURL(file);
  const a = document.createElement('a');
  a.href = fileURL;
  a.download = `report-${startDate}-${endDate}.pdf`;
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  return file;
}
