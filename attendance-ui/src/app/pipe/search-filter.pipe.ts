import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchFilter',
})
export class SearchFilterPipe implements PipeTransform {
  transform(value: any[], searchText: any): any[] {
    if (!value) return [];
    if (!searchText) return value;

    searchText = searchText.toLowerCase();
    return value.filter((item) => {
      return Object.keys(item).some((key) => {
        const val = item[key];
        return (
          typeof val === 'string' && val.toLowerCase().includes(searchText)
        );
      });
    });
  }
}
