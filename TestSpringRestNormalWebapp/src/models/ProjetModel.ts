import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'

export interface IProjet {
  budgetEstime?: number
  dateDebut?: string
  id?: number
  nomProjet?: string
}

export class Projet extends BaseModel implements IProjet {
  budgetEstime?: number
  dateDebut?: string
  id?: number
  nomProjet?: string

  constructor(data?: Partial<IProjet>) {
    super()
    this.budgetEstime = data?.budgetEstime
    this.dateDebut = data?.dateDebut
    this.id = data?.id
    this.nomProjet = data?.nomProjet
  }

  static override getKey(): string {
    return 'id'
  }

  public override getKeyValue(): string {
    return String(this.id)
  }

  static override getReferenceKey(): string {
    return 'budgetEstime'
  }

  public override getReferenceValue(): string {
    return String(this.budgetEstime)
  }

  protected static override searchDaoMetadata: EntitySearchField[] = [
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
      key: 'id',
      label: 'Id',
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
  ]
}


export class ProjetFormDTO {
  budgetEstime?: number
  dateDebut?: string
  id?: number
  nomProjet?: string

  constructor(data?: Partial<ProjetFormDTO>) {
    this.budgetEstime = data?.budgetEstime
    this.dateDebut = data?.dateDebut ? data.dateDebut : undefined
    
    this.id = data?.id
    this.nomProjet = data?.nomProjet
  }

  static parse(data?: Partial<IProjet> | IProjet | null) {
    const instance = new ProjetFormDTO()
    instance.budgetEstime = data?.budgetEstime
    instance.dateDebut = data?.dateDebut
    instance.id = data?.id
    instance.nomProjet = data?.nomProjet
    return instance
  }

  static parseList(data?: Projet[] | null): ProjetFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => ProjetFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<Projet>> {
    const entity: Partial<Projet> = new Projet({
      budgetEstime: this.budgetEstime,
      
      dateDebut: this.dateDebut ? this.dateDebut.split('T')[0] : undefined,
      
      id: this.id,
      
      nomProjet: this.nomProjet,
      
    })

    return entity
  }

  static async toEntities(data?: ProjetFormDTO[] | null){
      const entities: Promise<Partial<Projet>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

