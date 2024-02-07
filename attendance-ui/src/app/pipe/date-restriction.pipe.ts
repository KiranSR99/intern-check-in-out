import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateRestriction'
})
export class DateFilterPipe implements PipeTransform {
  transform(dates: Date[]): Date[] {
    const today: Date = new Date();
    const filteredDates: Date[] = [];

    dates.forEach(date => {
      const selectedDate: Date = new Date(date);
      // Check if selected date is today or in the future, and not Friday or Saturday
      if (selectedDate >= today && selectedDate.getDay() !== 5 && selectedDate.getDay() !== 6) {
        filteredDates.push(selectedDate);
      }
    });

    return filteredDates;
  }
}
