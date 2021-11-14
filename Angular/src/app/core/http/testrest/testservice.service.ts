import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { PlatformLocation } from '@angular/common';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { StringResponse } from '../../../shared/models/string-response';

@Injectable({
    providedIn: 'root'
})
export class TestserviceService {

    private _testApi = '/getAll';  // URL to web api
    private _reqOptionsArgs = { headers: new HttpHeaders().set('Content-Type', 'application/json') };

    constructor(private http: HttpClient, private pl: PlatformLocation) {
    }

    testJavaApi(): Observable<StringResponse> {
        const url = environment.apiResourceUri + this._testApi;
        console.log(url);
        return this.http.get<StringResponse>(url, this._reqOptionsArgs);
    }
}
