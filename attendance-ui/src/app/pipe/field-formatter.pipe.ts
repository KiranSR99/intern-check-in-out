import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'fieldFormatter',
})
export class FieldFormatterPipe implements PipeTransform {
  transform(value: number): string {
    if(value === 0){
      return 'Frontend';
    }
    else{
      return 'Backend';
    }
  }
}
