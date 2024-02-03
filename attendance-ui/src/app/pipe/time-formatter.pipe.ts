import { Pipe, PipeTransform } from '@angular/core';
import { formatDate } from '@angular/common';

@Pipe({
  name: 'timeFormatter'
})
export class TimeFormatterPipe implements PipeTransform {
  transform(value: any, format: string = 'shortTime', timezone: string = 'UTC', locale: string = 'en-US'): any {
    // Check if value is null or undefined
    if (!value) return value;

    try {
      return formatDate(value, format, locale, timezone);
    } catch (error) {
      console.warn('Could not format time:', value);
      return value;
    }
  }
}
