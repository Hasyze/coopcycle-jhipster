import { IProduit } from 'app/shared/model/produit.model';
import { ILivraison } from 'app/shared/model/livraison.model';

export interface IClient {
  id?: number;
  idClient?: number;
  prenomClient?: string;
  nomClient?: string;
  adresseClient?: string;
  email?: string;
  telClient?: string;
  produits?: IProduit[] | null;
  livraisons?: ILivraison[] | null;
}

export const defaultValue: Readonly<IClient> = {};
