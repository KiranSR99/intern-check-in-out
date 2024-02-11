import { formatDate } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateFormatter'
})
export class DateFormatterPipe implements PipeTransform {

  
    transform(value: any, format: string = 'mediumDate', timezone: string = 'UTC', locale: string = 'en-US'): string {
      if (!value) return value;
  
      try {
        return formatDate(value, format, locale, timezone);
      } catch (error) {
        console.warn('Could not format date:', value);
        return value;
      }
    }

  
  }

