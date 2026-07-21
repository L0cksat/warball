import { Component, inject, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { PlayerService } from '../../../services/player';
import { Player, PlayerAttributes } from '../../../models/player/player';
import { Team } from '../../../models/team/team';

@Component({
  selector: 'app-admin-player-form',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './admin-player-form.html',
  styleUrl: './admin-player-form.css',
})
export class AdminPlayerFormComponent implements OnInit{
  teamId: number | null = null
  playerId: number | null = null
  loadedPlayer: Player | null = null

  private fb = inject(FormBuilder)
  private router = inject(Router)
  private playerService = inject(PlayerService)
  private route = inject(ActivatedRoute)

  form = this.fb.group({
    playerId: ['', Validators.required],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    race: [''],
    subRace: [''],
    age: [null as number | null],
    position: ['', Validators.required],
  })

  private emptyAttributes(): PlayerAttributes{
    return {
      technicalAttributes: {
        siege: 0,
        trajectory: 0,
        threading: 0,
        magnetism: 0,
        castleThirst: 0,
      },
      physicalAttributes: {
        heft: 0,
        slipperiness: 0,
        stickyFingers: 0,
        paranoia: 0,
        bulwark: 0,
      },
      mysticalAttributes: {
        arcaneSpark: 0,
        ward: 0,
        restraint: 0,
      },
      vibeAttributes: {
        trashTalk: 0,
        moxie: 0,
        dramaticFlair: 0,
      }
    }
  }

  ngOnInit(): void {
    const teamParam = this.route.snapshot.paramMap.get('id')
    if (teamParam){
      this.teamId = +teamParam
    }

      const playerParam =this.route.snapshot.paramMap.get('playerId')
      if(playerParam){
        this.playerId = +playerParam
        this.playerService.getPlayer(this.playerId).subscribe({
          next: (player) => {
            this.loadedPlayer = player
          this.form.patchValue({
            playerId: player.playerId,
            firstName: player.firstName,
            lastName: player.lastName,
            race: player.race,
            subRace: player.subRace,
            age: player.age,
            position: player.position,
        })
      },
      error: () => {
        this.router.navigate(['/admin/teams', this.teamId, 'players'])
      }
      })
    } else {
      this.playerId = null
      this.loadedPlayer = null
    }
  }

  onSubmit(): void {
    if (this.form.invalid) return

    const player = {
      playerId: this.form.value.playerId!,
      firstName: this.form.value.firstName!,
      lastName: this.form.value.lastName!,
      race: this.form.value.race!,
      subRace: this.form.value.subRace!,
      age: this.form.value.age!,
      position: this.form.value.position!,
      team: { id: this.teamId! } as Team,
      attributes: this.loadedPlayer?.attributes ?? this.emptyAttributes(),
      traits: this.loadedPlayer?.traits ?? [],
      statusEffects: this.loadedPlayer?.statusEffects ?? [],
    }

    if (this.playerId){
      this.playerService.updatePlayer(this.playerId, player).subscribe({
        next: () => this.router.navigate(['/admin/teams', this.teamId, 'players'])
      })
    } else {
      this.playerService.createPlayer(player).subscribe({
        next: () => this.router.navigate(['/admin/teams', this.teamId, 'players'])
      })
      
    }
  }
}
