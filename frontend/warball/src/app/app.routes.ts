import { Routes } from '@angular/router';
import { TeamListComponent } from './components/team-list/team-list';
import { HomeComponent } from './components/home/home';
import { AdminTeamListComponent } from './components/admin/admin-team-list/admin-team-list';
import { AdminTeamFormComponent } from './components/admin/admin-team-form/admin-team-form';
import { AdminPlayerListComponent } from './components/admin/admin-player-list/admin-player-list';
import { AdminPlayerFormComponent } from './components/admin/admin-player-form/admin-player-form';
import { PlayerListComponent } from './components/player-list/player-list';
import { PlayerDetailsComponent } from './components/player-details/player-details';

export const routes: Routes = [
    { path: "", component: HomeComponent, pathMatch: 'full'},
    { path: "teams", component: TeamListComponent },
    { path: 'teams/:id/players', component: PlayerListComponent },
    { path: "teams/:id/players/:playerId/details", component: PlayerDetailsComponent },
    { path: 'admin/teams', component: AdminTeamListComponent },
    { path: 'admin/teams/new', component: AdminTeamFormComponent },
    { path: 'admin/teams/:id/edit', component: AdminTeamFormComponent },
    { path: 'admin/teams/:id/players', component: AdminPlayerListComponent},
    { path: 'admin/teams/:id/players/new', component: AdminPlayerFormComponent},
    { path: 'admin/teams/:id/players/:playerId/edit', component: AdminPlayerFormComponent},
];
