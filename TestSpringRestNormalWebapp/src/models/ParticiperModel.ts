import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'
import { createSelectSearchFunction, createMulticriteriatSearchFunction } from './SelectOption'
import { Personne } from './PersonneModel'
import * as personneService from '@/services/PersonneService'
import { Projet } from './ProjetModel'
import * as projetService from '@/services/ProjetService'

export interface IParticiper {
  id?: number
  idpersonnePersonne?: Personne
  idprojetProjet?: Projet
  dateInscription?: Date
  roleDansProjet?: string
}

export class Participer extends BaseModel implements IParticiper {
  id?: number
  idpersonnePersonne?: Personne
  idprojetProjet?: Projet
  dateInscription?: Date
  roleDansProjet?: string

  constructor(data?: Partial<IParticiper>) {
    super()
    this.id = data?.id
    this.idpersonnePersonne = data?.idpersonnePersonne
      ? new Personne(data?.idpersonnePersonne)
      : undefined
    this.idprojetProjet = data?.idprojetProjet
      ? new Projet(data?.idprojetProjet)
      : undefined
    this.dateInscription = data?.dateInscription
    this.roleDansProjet = data?.roleDansProjet
  }

  static override getKey(): string {
    return 'id'
  }

  public override getKeyValue(): string {
    return String(this.id)
  }

  static override getReferenceKey(): string {
    return 'dateInscription'
  }

  public override getReferenceValue(): string {
    return String(this.dateInscription)
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
      key: 'idprojetProjet',
      label: 'Projet',
      type: 'select',
      searchKey: Projet.getKey(),
      sortable: false,
      selectSearch: createSelectSearchFunction(Projet, projetService),
      multicriteriaSelect: {
        filters: Projet.getAllSearchFieldsMetadata(),
        searchFunction: createMulticriteriatSearchFunction(projetService),
      },
      showInTable: true,
      showInFilter: true,
    },
    
    { 
      key: 'dateInscription',
      label: 'Date inscription',
      type: 'datetime-local',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'roleDansProjet',
      label: 'Role dans projet',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
  ]
}


export class ParticiperFormDTO {
  id?: number
  idpersonnePersonne?: string
  
  idprojetProjet?: string
  
  dateInscription?: Date
  roleDansProjet?: string

  constructor(data?: Partial<ParticiperFormDTO>) {
    this.id = data?.id
    this.idpersonnePersonne = data?.idpersonnePersonne ?? ""
    
    this.idprojetProjet = data?.idprojetProjet ?? ""
    
    this.dateInscription = data?.dateInscription
    this.roleDansProjet = data?.roleDansProjet
  }

  static parse(data?: Partial<IParticiper> | IParticiper | null) {
    const instance = new ParticiperFormDTO()
    instance.id = data?.id
    instance.idpersonnePersonne = data?.idpersonnePersonne?.getKeyValue()?.toString()
    
    instance.idprojetProjet = data?.idprojetProjet?.getKeyValue()?.toString()
    
    instance.dateInscription = data?.dateInscription
    instance.roleDansProjet = data?.roleDansProjet
    return instance
  }

  static parseList(data?: Participer[] | null): ParticiperFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => ParticiperFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<Participer>> {
    const entity: Partial<Participer> = new Participer({
      id: this.id,
      
      dateInscription: this.dateInscription,
      
      roleDansProjet: this.roleDansProjet,
      
    })

    if (this.idpersonnePersonne && this.idpersonnePersonne.length > 0) {
      const related = await personneService.getById(Number(this.idpersonnePersonne));
      entity.idpersonnePersonne = related.data as Personne;
    }
    if (this.idprojetProjet && this.idprojetProjet.length > 0) {
      const related = await projetService.getById(Number(this.idprojetProjet));
      entity.idprojetProjet = related.data as Projet;
    }
    return entity
  }

  static async toEntities(data?: ParticiperFormDTO[] | null){
      const entities: Promise<Partial<Participer>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

