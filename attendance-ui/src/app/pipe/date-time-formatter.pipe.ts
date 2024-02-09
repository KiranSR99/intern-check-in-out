import { Pipe, PipeTransform } from '@angular/core';
import { formatDate } from '@angular/common';

@Pipe({
  name: 'customDateTimeFormatter',
})
export class CustomDateTimeFormatterPipe implements PipeTransform {
  transform(value: any): string {
    if (!value) return value;

    try {
      if (/^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}.\d{3}\+\d{2}:\d{2}$/.test(value)) {
        const dateObject = new Date(value);
        return formatDate(dateObject, 'medium', 'en-US');
      } else {
        const dateParts = value.split(' ');
        const date = dateParts[0].split('-').reverse().join('-');
        const formattedValue = `${date} ${dateParts[1]}`;

        const dateObject = new Date(formattedValue);
        return formatDate(dateObject, 'medium', 'en-US');
      }
    } catch (error) {
      console.warn('Could not format date:', value);
      return value;
    }
  }
}
