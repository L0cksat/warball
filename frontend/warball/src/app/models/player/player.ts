import { Team } from '../team/team';

export interface TechnicalAttributes {
  siege: number;
  trajectory: number;
  threading: number;
  magnetism: number;
  castleThirst: number;
}

export interface PhysicalAttributes {
  heft: number;
  slipperiness: number;
  stickyFingers: number;
  paranoia: number;
  bulwark: number;
}

export interface MysticalAttributes {
  arcaneSpark: number;
  ward: number;
  restraint: number;
}

export interface VibeAttributes {
  trashTalk: number;
  moxie: number;
  dramaticFlair: number;
}

export interface PlayerAttributes {
  technicalAttributes: TechnicalAttributes;
  physicalAttributes: PhysicalAttributes;
  mysticalAttributes: MysticalAttributes;
  vibeAttributes: VibeAttributes;
}

export interface Trait {
  traitId: string;
  name: string;
  type: string;
}

/** Backend StatusEffect is currently empty — extend when fields are added. */
export interface StatusEffect {}

export interface Player {
  id?: number;
  playerId: string;
  firstName: string;
  lastName: string;
  age: number;
  race: string;
  subRace: string;
  position: string;
  team?: Team;
  attributes: PlayerAttributes;
  traits: Trait[];
  statusEffects: StatusEffect[];
  hobbies: string[];
  shirtNumber: number | null;
}
