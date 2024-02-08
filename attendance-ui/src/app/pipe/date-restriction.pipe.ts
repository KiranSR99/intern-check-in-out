import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateRestriction'
})
export class DateFilterPipe implements PipeTransform {
  transform(dates: Date | Date[]): Date | Date[] {
    if (Array.isArray(dates)) {
      return dates.filter(date => this.isValidDate(date));
    } else {
      return this.isValidDate(dates) ? dates : []; 
    }
  }

  private isValidDate(date: Date): boolean {
    const today: Date = new Date();
    today.setHours(0, 0, 0, 0);

    return date >= today && date.getDay() !== 5 && date.getDay() !== 6;
  }
}
