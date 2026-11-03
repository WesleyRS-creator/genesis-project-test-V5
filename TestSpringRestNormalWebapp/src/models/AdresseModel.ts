import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'
import { createSelectSearchFunction, createMulticriteriatSearchFunction } from './SelectOption'
import { Personne } from './PersonneModel'
import * as personneService from '@/services/PersonneService'

export interface IAdresse {
  id?: number
  idpersonnePersonne?: Personne
  latitude?: number
  codePostal?: string
  ville?: string
  rue?: string
}

export class Adresse extends BaseModel implements IAdresse {
  id?: number
  idpersonnePersonne?: Personne
  latitude?: number
  codePostal?: string
  ville?: string
  rue?: string

  constructor(data?: Partial<IAdresse>) {
    super()
    this.id = data?.id
    this.idpersonnePersonne = data?.idpersonnePersonne
      ? new Personne(data?.idpersonnePersonne)
      : undefined
    this.latitude = data?.latitude
    this.codePostal = data?.codePostal
    this.ville = data?.ville
    this.rue = data?.rue
  }

  static override getKey(): string {
    return 'id'
  }

  public override getKeyValue(): string {
    return String(this.id)
  }

  static override getReferenceKey(): string {
    return 'latitude'
  }

  public override getReferenceValue(): string {
    return String(this.latitude)
  }

  protected static override searchDaoMetadata: EntitySearchField[] = [
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
      key: 'idpersonnePersonne',
      label: 'Personne',
      type: 'select',
      searchKey: Personne.getKey(),
      sortable: false,
      selectSearch: createSelectSearchFunction(Personne, personneService),
      multicriteriaSelect: {
        filters: Personne.getAllSearchFieldsMetadata(),
        searchFunction: createMulticriteriatSearchFunction(personneService),
      },
      showInTable: true,
      showInFilter: true,
    },
    
    { 
      key: 'latitude',
      label: 'Latitude',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'latitudeMin',
      label: 'Latitude Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'latitudeMax',
      label: 'Latitude Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'codePostal',
      label: 'Code postal',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'ville',
      label: 'Ville',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'rue',
      label: 'Rue',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
  ]
}


export class AdresseFormDTO {
  id?: number
  idpersonnePersonne?: string
  
  latitude?: number
  codePostal?: string
  ville?: string
  rue?: string

  constructor(data?: Partial<AdresseFormDTO>) {
    this.id = data?.id
    this.idpersonnePersonne = data?.idpersonnePersonne ?? ""
    
    this.latitude = data?.latitude
    this.codePostal = data?.codePostal
    this.ville = data?.ville
    this.rue = data?.rue
  }

  static parse(data?: Partial<IAdresse> | IAdresse | null) {
    const instance = new AdresseFormDTO()
    instance.id = data?.id
    instance.idpersonnePersonne = data?.idpersonnePersonne?.getKeyValue()?.toString()
    
    instance.latitude = data?.latitude
    instance.codePostal = data?.codePostal
    instance.ville = data?.ville
    instance.rue = data?.rue
    return instance
  }

  static parseList(data?: Adresse[] | null): AdresseFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => AdresseFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<Adresse>> {
    const entity: Partial<Adresse> = new Adresse({
      id: this.id,
      
      latitude: this.latitude,
      
      codePostal: this.codePostal,
      
      ville: this.ville,
      
      rue: this.rue,
      
    })

    if (this.idpersonnePersonne && this.idpersonnePersonne.length > 0) {
      const related = await personneService.getById(Number(this.idpersonnePersonne));
      entity.idpersonnePersonne = related.data as Personne;
    }
    return entity
  }

  static async toEntities(data?: AdresseFormDTO[] | null){
      const entities: Promise<Partial<Adresse>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

