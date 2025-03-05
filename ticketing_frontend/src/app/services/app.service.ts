import {Injectable} from '@angular/core';
import { saveAs } from 'file-saver';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor() {
  }

  csvDownload(headers: any, data: any) {
    if (!data || !data.length) {
      return;
    }
    const separator = ',';
    const csvData = headers.join(separator) + '\n' + data.map((row: any) => {
      return headers.map((headerKey: any) => {
        return row[headerKey] === null || row[headerKey] === undefined ? "" : row[headerKey];
      }).join(separator);
    }).join('\n');
    this.exportCsv(csvData, 'ticketing-day-report.csv'); // download to csv
  }

  private exportCsv(csvData: string, csv: string) {
    // @ts-ignore
    const csvFile = new Blob([csvData], {type: 'text/csv;charset=utf-8;'});
    saveAs(csvFile, csv);
  }

}
