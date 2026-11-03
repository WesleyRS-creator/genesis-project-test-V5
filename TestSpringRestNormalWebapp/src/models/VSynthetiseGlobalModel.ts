import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'

export interface IVSynthetiseGlobal {
  idPersonne?: number
  nomComplet?: string
  trancheAge?: string
  totalParticipations?: number
  rolePrincipal?: string
  budgetMoyenProjets?: number
  budgetGlobalMoyen?: number
  positionBudget?: string
  nombreVilles?: number
  latitudeMoyenne?: number
}

export class VSynthetiseGlobal extends BaseModel implements IVSynthetiseGlobal {
  idPersonne?: number
  nomComplet?: string
  trancheAge?: string
  totalParticipations?: number
  rolePrincipal?: string
  budgetMoyenProjets?: number
  budgetGlobalMoyen?: number
  positionBudget?: string
  nombreVilles?: number
  latitudeMoyenne?: number

  constructor(data?: Partial<IVSynthetiseGlobal>) {
    super()
    this.idPersonne = data?.idPersonne
    this.nomComplet = data?.nomComplet
    this.trancheAge = data?.trancheAge
    this.totalParticipations = data?.totalParticipations
    this.rolePrincipal = data?.rolePrincipal
    this.budgetMoyenProjets = data?.budgetMoyenProjets
    this.budgetGlobalMoyen = data?.budgetGlobalMoyen
    this.positionBudget = data?.positionBudget
    this.nombreVilles = data?.nombreVilles
    this.latitudeMoyenne = data?.latitudeMoyenne
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
      key: 'nomComplet',
      label: 'Nom complet',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'trancheAge',
      label: 'Tranche age',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'totalParticipations',
      label: 'Total participations',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'totalParticipationsMin',
      label: 'Total participations Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'totalParticipationsMax',
      label: 'Total participations Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'rolePrincipal',
      label: 'Role principal',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'budgetMoyenProjets',
      label: 'Budget moyen projets',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'budgetMoyenProjetsMin',
      label: 'Budget moyen projets Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'budgetMoyenProjetsMax',
      label: 'Budget moyen projets Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'budgetGlobalMoyen',
      label: 'Budget global moyen',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'budgetGlobalMoyenMin',
      label: 'Budget global moyen Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'budgetGlobalMoyenMax',
      label: 'Budget global moyen Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'positionBudget',
      label: 'Position budget',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'nombreVilles',
      label: 'Nombre villes',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'nombreVillesMin',
      label: 'Nombre villes Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'nombreVillesMax',
      label: 'Nombre villes Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'latitudeMoyenne',
      label: 'Latitude moyenne',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'latitudeMoyenneMin',
      label: 'Latitude moyenne Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'latitudeMoyenneMax',
      label: 'Latitude moyenne Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
  ]
}



