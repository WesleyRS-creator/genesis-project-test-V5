package com.example.testspringrestnormal.specification;

import com.example.testspringrestnormal.models.Participer;
import com.example.testspringrestnormal.models.dto.ParticiperSearch;
import com.example.testspringrestnormal.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ParticiperSpecification {

    public static Specification<Participer> filter(ParticiperSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            System.out.println("🔍 TEMPLATE DEBUG: name=id | columnType=[int4] | isText=false | isNumeric=true");

            
            if(object.getId()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("id"), object.getId()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idpersonnePersonne | columnType=[integer] | isText=false | isNumeric=true");

            
            if(object.getIdpersonnePersonne()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idpersonnePersonne"), object.getIdpersonnePersonne()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=idprojetProjet | columnType=[integer] | isText=false | isNumeric=true");

            
            if(object.getIdprojetProjet()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idprojetProjet"), object.getIdprojetProjet()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=dateInscription | columnType=[timestamp] | isText=false | isNumeric=false");

            
            if(object.getDateInscription()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("dateInscription"), object.getDateInscription()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("dateInscription"), object.getDateInscriptionMin(), object.getDateInscriptionMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=roleDansProjet | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("roleDansProjet"), object.getRoleDansProjet(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
