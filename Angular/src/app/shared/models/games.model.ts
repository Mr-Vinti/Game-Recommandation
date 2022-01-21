import { Game } from './game.model';
import { User } from './user.model';

export interface Games {
    user: User;
    description: string;
    games: Game[];
}
