import { DatePipe } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'timeFormatter'
})

export class TimeFormatterPipe implements PipeTransform {
   
    transform(value: any, format: string = 'shortTime'): any {
        // Use Angular's DatePipe for formatting
        const datePipe = new DatePipe('en-US'); // Use your locale
        return datePipe.transform(new Date(), format);
      }

}
  
