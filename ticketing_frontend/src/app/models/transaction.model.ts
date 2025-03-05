import {UserModel} from "./user.model";

export interface TransactionModel {
  id: number;
  codeTicket: string;
  montant: number;
  typePaiement: string;
  reference: string;
  transactionId: string;
  telephone: string;
  operateur: string;
  dateExpiration: Date;
  typePrestation: string;
  user: UserModel;
  createdAt: Date;
}
