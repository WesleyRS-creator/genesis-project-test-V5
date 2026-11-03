package com.example.testspringrestnormal.specification;

import com.example.testspringrestnormal.models.VFicheCompletePersonne;
import com.example.testspringrestnormal.models.dto.VFicheCompletePersonneSearch;
import com.example.testspringrestnormal.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class VFicheCompletePersonneSpecification {

    public static Specification<VFicheCompletePersonne> filter(VFicheCompletePersonneSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            System.out.println("🔍 TEMPLATE DEBUG: name=idPersonne | columnType=[int4] | isText=false | isNumeric=true");

            
            if(object.getIdPersonne()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idPersonne"), object.getIdPersonne()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=nom | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("nom"), object.getNom(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=prenom | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("prenom"), object.getPrenom(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=dateNaissance | columnType=[date] | isText=false | isNumeric=false");

            
            if(object.getDateNaissance()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("dateNaissance"), object.getDateNaissance()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("dateNaissance"), object.getDateNaissanceMin(), object.getDateNaissanceMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=age | columnType=[int4] | isText=false | isNumeric=true");

            
            if(object.getAge()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("age"), object.getAge()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("age"), object.getAgeMin(), object.getAgeMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=biographie | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("biographie"), object.getBiographie(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=numeroSs | columnType=[int8] | isText=false | isNumeric=true");

            
            if(object.getNumeroSs()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("numeroSs"), object.getNumeroSs()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("numeroSs"), object.getNumeroSsMin(), object.getNumeroSsMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=numeroPasseport | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("numeroPasseport"), object.getNumeroPasseport(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=dateExpiration | columnType=[date] | isText=false | isNumeric=false");

            
            if(object.getDateExpiration()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("dateExpiration"), object.getDateExpiration()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("dateExpiration"), object.getDateExpirationMin(), object.getDateExpirationMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=statutPasseport | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("statutPasseport"), object.getStatutPasseport(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=nombreAdresses | columnType=[int8] | isText=false | isNumeric=true");

            
            if(object.getNombreAdresses()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("nombreAdresses"), object.getNombreAdresses()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("nombreAdresses"), object.getNombreAdressesMin(), object.getNombreAdressesMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=villePrincipale | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("villePrincipale"), object.getVillePrincipale(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=nombreProjets | columnType=[int8] | isText=false | isNumeric=true");

            
            if(object.getNombreProjets()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("nombreProjets"), object.getNombreProjets()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("nombreProjets"), object.getNombreProjetsMin(), object.getNombreProjetsMax(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
