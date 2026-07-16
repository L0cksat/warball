import { Component, inject, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { TeamService } from '../../../services/team';


@Component({
  selector: 'app-admin-team-form',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './admin-team-form.html',
  styleUrl: './admin-team-form.css',
})

export class AdminTeamFormComponent implements OnInit{
  teamId: number | null = null

private fb = inject(FormBuilder)
private router = inject(Router)
private teamService = inject(TeamService)
private route = inject(ActivatedRoute)

  form = this.fb.group({
    teamName: ['', Validators.required],
    country: [''],
    province: ['']
  })

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if(idParam) {
      this.teamId = +idParam
        this.teamService.getTeam(this.teamId!).subscribe({
          next: (team) => {
            this.form.patchValue({
              teamName: team.teamName,
              country: team.country,
              province: team.province ?? ''
            })
          },
          error: () => {
            this.router.navigate(['/admin/teams'])
          }
        })
    } else {
        this.teamId = null

    }
  }

  onSubmit(): void {
    if(this.form.invalid) return

    const team = {
      teamName: this.form.value.teamName!,
      country: this.form.value.country!,
      province: this.form.value.province || null,
    }

    if(this.teamId) {
      this.teamService.updateTeam(this.teamId, team).subscribe({
        next: () => this.router.navigate(['/admin/teams'])
      })
    } else {
      this.teamService.createTeam(team).subscribe({
        next: () => this.router.navigate(['/admin/teams'])
      })
    }
  }

  
}
