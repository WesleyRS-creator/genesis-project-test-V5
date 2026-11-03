import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'

export interface IPersonne {
  dateNaissance?: string
  id?: number
  numeroSs?: number
  nom?: string
  prenom?: string
  biographie?: string
}

export class Personne extends BaseModel implements IPersonne {
  dateNaissance?: string
  id?: number
  numeroSs?: number
  nom?: string
  prenom?: string
  biographie?: string

  constructor(data?: Partial<IPersonne>) {
    super()
    this.dateNaissance = data?.dateNaissance
    this.id = data?.id
    this.numeroSs = data?.numeroSs
    this.nom = data?.nom
    this.prenom = data?.prenom
    this.biographie = data?.biographie
  }

  static override getKey(): string {
    return 'id'
  }

  public override getKeyValue(): string {
    return String(this.id)
  }

  static override getReferenceKey(): string {
    return 'dateNaissance'
  }

  public override getReferenceValue(): string {
    return String(this.dateNaissance)
  }

  protected static override searchDaoMetadata: EntitySearchField[] = [
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
      key: 'id',
      label: 'Id',
      type: 'string',
      sortable: true,
      showInTable: true,
      showInFilter: true,
      identifier: true 
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
      key: 'biographie',
      label: 'Biographie',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
  ]
}


export class PersonneFormDTO {
  dateNaissance?: string
  id?: number
  numeroSs?: number
  nom?: string
  prenom?: string
  biographie?: string

  constructor(data?: Partial<PersonneFormDTO>) {
    this.dateNaissance = data?.dateNaissance ? data.dateNaissance : undefined
    
    this.id = data?.id
    this.numeroSs = data?.numeroSs
    this.nom = data?.nom
    this.prenom = data?.prenom
    this.biographie = data?.biographie
  }

  static parse(data?: Partial<IPersonne> | IPersonne | null) {
    const instance = new PersonneFormDTO()
    instance.dateNaissance = data?.dateNaissance
    instance.id = data?.id
    instance.numeroSs = data?.numeroSs
    instance.nom = data?.nom
    instance.prenom = data?.prenom
    instance.biographie = data?.biographie
    return instance
  }

  static parseList(data?: Personne[] | null): PersonneFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => PersonneFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<Personne>> {
    const entity: Partial<Personne> = new Personne({
      dateNaissance: this.dateNaissance ? this.dateNaissance.split('T')[0] : undefined,
      
      id: this.id,
      
      numeroSs: this.numeroSs,
      
      nom: this.nom,
      
      prenom: this.prenom,
      
      biographie: this.biographie,
      
    })

    return entity
  }

  static async toEntities(data?: PersonneFormDTO[] | null){
      const entities: Promise<Partial<Personne>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

