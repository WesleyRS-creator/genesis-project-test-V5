package com.example.testspringrestnormal.specification;

import com.example.testspringrestnormal.models.Personne;
import com.example.testspringrestnormal.models.dto.PersonneSearch;
import com.example.testspringrestnormal.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class PersonneSpecification {

    public static Specification<Personne> filter(PersonneSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            System.out.println("🔍 TEMPLATE DEBUG: name=dateNaissance | columnType=[date] | isText=false | isNumeric=false");

            
            if(object.getDateNaissance()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("dateNaissance"), object.getDateNaissance()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("dateNaissance"), object.getDateNaissanceMin(), object.getDateNaissanceMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=id | columnType=[int4] | isText=false | isNumeric=true");

            
            if(object.getId()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("id"), object.getId()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=numeroSs | columnType=[int8] | isText=false | isNumeric=true");

            
            if(object.getNumeroSs()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("numeroSs"), object.getNumeroSs()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("numeroSs"), object.getNumeroSsMin(), object.getNumeroSsMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=nom | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("nom"), object.getNom(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=prenom | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("prenom"), object.getPrenom(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=biographie | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("biographie"), object.getBiographie(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
