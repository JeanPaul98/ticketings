import {RoleModel} from "./role.model";

export interface UserModel {
  id: number;
  username: string;
  password: string;
  nom: string;
  prenom: string;
  telephone: string;
  fonction: string;
  actif: boolean;
  dateConnexion: Date;
  dateDeconnexion: Date;
  createdAt: Date;
  updatedAt: Date;
  roles: RoleModel[];
}
