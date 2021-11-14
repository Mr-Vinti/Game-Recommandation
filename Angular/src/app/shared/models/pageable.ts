import { HttpParams } from '@angular/common/http';
import { environment } from './../../../environments/environment';

export class Pageable {
  page: number;
  size: number;
  sort: PageableSort[];

  constructor(
    page: number = 0,
    size: number = 5,
    sort: PageableSort[] = []
  ) {
    this.page = page;
    this.size = size;
    this.sort = sort;
  }

  createPageableParams(httpParams: HttpParams): HttpParams {
    httpParams = httpParams.set('page', this.page.toString());
    httpParams = httpParams.set('size', this.size.toString());
    this.sort.forEach((sortItem) => {
      if (sortItem.field) {
        httpParams = httpParams.append(
          'sort',
          `${sortItem.field},${sortItem.order}`
        );
      }
    });
    return httpParams;
  }
}

export interface IPageableSort {
  field: string;
  order: PageableSortOrder;
}

export class PageableSort implements IPageableSort {
  field: string;
  order: PageableSortOrder;
}

export enum PageableSortOrder {
  ASC = 'asc',
  DESC = 'desc',
}
