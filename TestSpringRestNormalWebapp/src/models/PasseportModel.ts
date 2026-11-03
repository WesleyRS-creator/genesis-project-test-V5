import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'
import { createSelectSearchFunction, createMulticriteriatSearchFunction } from './SelectOption'
import { Personne } from './PersonneModel'
import * as personneService from '@/services/PersonneService'

export interface IPasseport {
  dateExpiration?: string
  id?: number
  idpersonnePersonne?: Personne
  numero?: string
}

export class Passeport extends BaseModel implements IPasseport {
  dateExpiration?: string
  id?: number
  idpersonnePersonne?: Personne
  numero?: string

  constructor(data?: Partial<IPasseport>) {
    super()
    this.dateExpiration = data?.dateExpiration
    this.id = data?.id
    this.idpersonnePersonne = data?.idpersonnePersonne
      ? new Personne(data?.idpersonnePersonne)
      : undefined
    this.numero = data?.numero
  }

  static override getKey(): string {
    return 'id'
  }

  public override getKeyValue(): string {
    return String(this.id)
  }

  static override getReferenceKey(): string {
    return 'dateExpiration'
  }

  public override getReferenceValue(): string {
    return String(this.dateExpiration)
  }

  protected static override searchDaoMetadata: EntitySearchField[] = [
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
      key: 'numero',
      label: 'Numero',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
  ]
}


export class PasseportFormDTO {
  dateExpiration?: string
  id?: number
  idpersonnePersonne?: string
  
  numero?: string

  constructor(data?: Partial<PasseportFormDTO>) {
    this.dateExpiration = data?.dateExpiration ? data.dateExpiration : undefined
    
    this.id = data?.id
    this.idpersonnePersonne = data?.idpersonnePersonne ?? ""
    
    this.numero = data?.numero
  }

  static parse(data?: Partial<IPasseport> | IPasseport | null) {
    const instance = new PasseportFormDTO()
    instance.dateExpiration = data?.dateExpiration
    instance.id = data?.id
    instance.idpersonnePersonne = data?.idpersonnePersonne?.getKeyValue()?.toString()
    
    instance.numero = data?.numero
    return instance
  }

  static parseList(data?: Passeport[] | null): PasseportFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => PasseportFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<Passeport>> {
    const entity: Partial<Passeport> = new Passeport({
      dateExpiration: this.dateExpiration ? this.dateExpiration.split('T')[0] : undefined,
      
      id: this.id,
      
      numero: this.numero,
      
    })

    if (this.idpersonnePersonne && this.idpersonnePersonne.length > 0) {
      const related = await personneService.getById(Number(this.idpersonnePersonne));
      entity.idpersonnePersonne = related.data as Personne;
    }
    return entity
  }

  static async toEntities(data?: PasseportFormDTO[] | null){
      const entities: Promise<Partial<Passeport>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

