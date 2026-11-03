package com.example.testspringrestnormal.specification;

import com.example.testspringrestnormal.models.Passeport;
import com.example.testspringrestnormal.models.dto.PasseportSearch;
import com.example.testspringrestnormal.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class PasseportSpecification {

    public static Specification<Passeport> filter(PasseportSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            System.out.println("🔍 TEMPLATE DEBUG: name=dateExpiration | columnType=[date] | isText=false | isNumeric=false");

            
            if(object.getDateExpiration()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("dateExpiration"), object.getDateExpiration()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("dateExpiration"), object.getDateExpirationMin(), object.getDateExpirationMax(), predicates);
            
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
            
            System.out.println("🔍 TEMPLATE DEBUG: name=numero | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("numero"), object.getNumero(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
