import { map } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { AlphaNumericCombination } from './combinations'
import { ApiService } from '../api.service';
import { Observable } from 'rxjs';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-combinations',
  templateUrl: './combinations.component.html',
  styleUrls: ['./combinations.component.css']
})
export class CombinationsComponent implements OnInit {
  
  limits = ['10', '25', '50'];

  apiService: ApiService;
  totalCount: number;
  combinations: string[];
  phoneNumberForm: FormGroup;

  limit: string = this.limits[0];
  pageNumber: number = -1;
  phnNumber: string = '1234567';
  validPhoneNumberPattern: string = '^(\\d{7})(\\d{3})?$';

  constructor(apiService: ApiService) {
    this.apiService = apiService;
  }

  get phoneNumber() { return this.phoneNumberForm.get('phoneNumber'); }
  get limitValue() { return this.phoneNumberForm.get('limitValue'); }

  ngOnInit(): void {
    this.phoneNumberForm = new FormGroup({
      'phoneNumber': new FormControl(this.phnNumber, [
        Validators.required,
        Validators.pattern(this.validPhoneNumberPattern)
      ]),
      'limitValue': new FormControl(this.limit, [
        Validators.required
      ])
    });
  }

  getCombinations() {

    this.pageNumber += 1;
    let phoneNumberFromValue = this.phoneNumberForm.value;

    this.apiService
      .getCombinations(phoneNumberFromValue.phoneNumber, this.pageNumber, phoneNumberFromValue.limitValue)
      .subscribe((data: AlphaNumericCombination) => {
        this.combinations = data.combinations;
        this.totalCount = data.totalCount;
      });
  }



}
