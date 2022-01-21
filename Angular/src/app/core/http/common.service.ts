import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { PlatformLocation } from '@angular/common';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { IPagedResponse } from '../../shared/models/IPagedResponse';
import { Pageable } from '../../shared/models/pageable';
import { User } from '../../shared/models/user.model';
import { Games } from '../../shared/models/games.model';

@Injectable({
  providedIn: 'root',
})
export class CommonService {
  constructor(private http: HttpClient, private pl: PlatformLocation) {}

  // registerUser(user: User): Observable<Invitation> {
  //   const url = environment.apiResourceUri + '/users' + '/register';

  //   return this.http.post<Invitation>(url, user);
  // }

  registerUser(): Observable<User> {
    const url = environment.apiResourceUri + '/users';

    return this.http.post<any>(url, null);
  }

  getUsers(uuid: string, page: Pageable): Observable<IPagedResponse<User>> {
    const url = environment.apiResourceUri + '/users';

    let params = new HttpParams();
    params = page.createPageableParams(params);
    params = params.set('userUuid', uuid);

    return this.http.get<IPagedResponse<User>>(url, { params });
  }

  getGames(games: Games): Observable<Games> {
    const url = environment.apiResourceUri + '/games';

    return this.http.post<Games>(url, games);
  }

  // getEventsWithParams(
  //   paramsMap: Map<string, object>,
  //   page: Pageable
  // ): Observable<IPagedResponse<Event>> {
  //   const url = environment.apiResourceUri + '/events';

  //   let params = new HttpParams();
  //   params = page.createPageableParams(params);
  //   paramsMap.forEach(
  //     (value, key) => (params = params.set(key, value.toString()))
  //   );

  //   return this.http.get<IPagedResponse<Event>>(url, { params });
  // }
}
