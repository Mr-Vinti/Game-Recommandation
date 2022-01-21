import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Pageable } from '../../shared/models/pageable';
import { User } from '../../shared/models/user.model';
import { CommonService } from '../../core/http/common.service';
import { Games } from '../../shared/models/games.model';
import { Game } from '../../shared/models/game.model';

@Component({
  selector: 'app-template',
  templateUrl: './template.component.html',
  styleUrls: ['./template.component.scss'],
})
export class TemplateComponent implements OnInit {
  form: FormGroup;
  formDesc: FormGroup;
  userRegistered: Boolean;
  invitationUrl: string;
  user: User = null;
  games: Game[] = null;

  constructor(
    private service: CommonService,
    private fb: FormBuilder,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      id: ['', [Validators.required]],
    });
    this.formDesc = this.fb.group({
      description: ['', [Validators.required]],
    });
  }

  submitWithId() {
    if (!this.form.valid) {
      this.form.markAllAsTouched();
      return;
    }

    this.service
      .getUsers(this.form.controls.id.value, new Pageable(0, 1))
      .subscribe((page) => {
        if (page.totalElements !== 1) {
          this._snackBar.open(
            'The ID you entered is invalid. Please make sure you typed it in correctly or start a new session',
            'Close', {
              duration: 300
            }
          );
          return;
        }
        this.user = page.content[0];
        console.log(this.user);

        const games: Games = {
          user: this.user,
          description: '',
          games: []
        };

        this.service.getGames(games).subscribe((response) => {
          this.games = response.games;
        });
      }, (error) => {
        this._snackBar.open(
          'The ID you entered is invalid. Please make sure you typed it in correctly or start a new session'
        );
      });
  }

  submitWithoutId() {
    this.service
      .registerUser()
      .subscribe((user) => {
        this.user = user;
        console.log(this.user);
      });
  }

  getGameSuggestions() {
    if (!this.formDesc.valid) {
      this.form.markAllAsTouched();
      return;
    }

    const description = this.formDesc.controls.description.value;

    const games: Games = {
      user: this.user,
      description: description,
      games: []
    };

    this.service.getGames(games).subscribe((response) => {
      this.games = response.games;
    });
  }
}
