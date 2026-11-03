import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'

export interface IVFicheCompletePersonne {
  idPersonne?: number
  nom?: string
  prenom?: string
  dateNaissance?: string
  age?: number
  biographie?: string
  numeroSs?: number
  numeroPasseport?: string
  dateExpiration?: string
  statutPasseport?: string
  nombreAdresses?: number
  villePrincipale?: string
  nombreProjets?: number
}

export class VFicheCompletePersonne extends BaseModel implements IVFicheCompletePersonne {
  idPersonne?: number
  nom?: string
  prenom?: string
  dateNaissance?: string
  age?: number
  biographie?: string
  numeroSs?: number
  numeroPasseport?: string
  dateExpiration?: string
  statutPasseport?: string
  nombreAdresses?: number
  villePrincipale?: string
  nombreProjets?: number

  constructor(data?: Partial<IVFicheCompletePersonne>) {
    super()
    this.idPersonne = data?.idPersonne
    this.nom = data?.nom
    this.prenom = data?.prenom
    this.dateNaissance = data?.dateNaissance
    this.age = data?.age
    this.biographie = data?.biographie
    this.numeroSs = data?.numeroSs
    this.numeroPasseport = data?.numeroPasseport
    this.dateExpiration = data?.dateExpiration
    this.statutPasseport = data?.statutPasseport
    this.nombreAdresses = data?.nombreAdresses
    this.villePrincipale = data?.villePrincipale
    this.nombreProjets = data?.nombreProjets
  }

  static override getKey(): string {
    return 'idPersonne'
  }

  public override getKeyValue(): string {
    return String(this.idPersonne)
  }

  static override getReferenceKey(): string {
    return 'idPersonne'
  }

  public override getReferenceValue(): string {
    return String(this.idPersonne)
  }

  protected static override searchDaoMetadata: EntitySearchField[] = [
    {
      key: 'idPersonne',
      label: 'Id personne',
      type: 'string',
      sortable: true,
      showInTable: true,
      showInFilter: true,
      identifier: true 
    },
    
    { 
      key: 'nom',
      label: 'Nom',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'prenom',
      label: 'Prenom',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'dateNaissance',
      label: 'Date naissance',
      type: 'date',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'dateNaissanceMin',
      label: 'Date naissance Min',
      type: 'date',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'dateNaissanceMax',
      label: 'Date naissance Max',
      type: 'date',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'age',
      label: 'Age',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'ageMin',
      label: 'Age Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'ageMax',
      label: 'Age Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'biographie',
      label: 'Biographie',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'numeroSs',
      label: 'Numero ss',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'numeroSsMin',
      label: 'Numero ss Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'numeroSsMax',
      label: 'Numero ss Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'numeroPasseport',
      label: 'Numero passeport',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'dateExpiration',
      label: 'Date expiration',
      type: 'date',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'dateExpirationMin',
      label: 'Date expiration Min',
      type: 'date',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'dateExpirationMax',
      label: 'Date expiration Max',
      type: 'date',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'statutPasseport',
      label: 'Statut passeport',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'nombreAdresses',
      label: 'Nombre adresses',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'nombreAdressesMin',
      label: 'Nombre adresses Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'nombreAdressesMax',
      label: 'Nombre adresses Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'villePrincipale',
      label: 'Ville principale',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'nombreProjets',
      label: 'Nombre projets',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'nombreProjetsMin',
      label: 'Nombre projets Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'nombreProjetsMax',
      label: 'Nombre projets Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
  ]
}



