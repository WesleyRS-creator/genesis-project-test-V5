import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'

export interface IVProjetsEtEquipe {
  idProjet?: number
  nomProjet?: string
  dateDebut?: string
  budgetEstime?: number
  dureeJours?: number
  nombreParticipants?: number
  budgetParParticipant?: number
  listeEquipe?: string
  rolesDistribues?: string
  derniereInscription?: Date
}

export class VProjetsEtEquipe extends BaseModel implements IVProjetsEtEquipe {
  idProjet?: number
  nomProjet?: string
  dateDebut?: string
  budgetEstime?: number
  dureeJours?: number
  nombreParticipants?: number
  budgetParParticipant?: number
  listeEquipe?: string
  rolesDistribues?: string
  derniereInscription?: Date

  constructor(data?: Partial<IVProjetsEtEquipe>) {
    super()
    this.idProjet = data?.idProjet
    this.nomProjet = data?.nomProjet
    this.dateDebut = data?.dateDebut
    this.budgetEstime = data?.budgetEstime
    this.dureeJours = data?.dureeJours
    this.nombreParticipants = data?.nombreParticipants
    this.budgetParParticipant = data?.budgetParParticipant
    this.listeEquipe = data?.listeEquipe
    this.rolesDistribues = data?.rolesDistribues
    this.derniereInscription = data?.derniereInscription
  }

  static override getKey(): string {
    return 'idProjet'
  }

  public override getKeyValue(): string {
    return String(this.idProjet)
  }

  static override getReferenceKey(): string {
    return 'idProjet'
  }

  public override getReferenceValue(): string {
    return String(this.idProjet)
  }

  protected static override searchDaoMetadata: EntitySearchField[] = [
    {
      key: 'idProjet',
      label: 'Id projet',
      type: 'string',
      sortable: true,
      showInTable: true,
      showInFilter: true,
      identifier: true 
    },
    
    { 
      key: 'nomProjet',
      label: 'Nom projet',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'dateDebut',
      label: 'Date debut',
      type: 'date',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'dateDebutMin',
      label: 'Date debut Min',
      type: 'date',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'dateDebutMax',
      label: 'Date debut Max',
      type: 'date',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'budgetEstime',
      label: 'Budget estime',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'budgetEstimeMin',
      label: 'Budget estime Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'budgetEstimeMax',
      label: 'Budget estime Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'dureeJours',
      label: 'Duree jours',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'dureeJoursMin',
      label: 'Duree jours Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'dureeJoursMax',
      label: 'Duree jours Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'nombreParticipants',
      label: 'Nombre participants',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'nombreParticipantsMin',
      label: 'Nombre participants Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'nombreParticipantsMax',
      label: 'Nombre participants Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'budgetParParticipant',
      label: 'Budget par participant',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'budgetParParticipantMin',
      label: 'Budget par participant Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'budgetParParticipantMax',
      label: 'Budget par participant Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'listeEquipe',
      label: 'Liste equipe',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'rolesDistribues',
      label: 'Roles distribues',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'derniereInscription',
      label: 'Derniere inscription',
      type: 'datetime-local',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
  ]
}



