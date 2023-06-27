import {Component, OnInit} from '@angular/core';
import {LogService} from "../../services/rest/log.service";

@Component({
  selector: 'app-request-counter',
  templateUrl: './request-counter.component.html',
  styleUrls: ['./request-counter.component.css']
})
export class RequestCounterComponent implements OnInit {

  constructor(private logService: LogService) {
  }

  protected requestCount = 0;

  ngOnInit() {
    this.getRequestCount();
  }

  protected getRequestCount() {
    this.logService.getRequestCount().subscribe(response => this.requestCount = response);
  }
}
