package com.example.testspringrestnormal.specification;

import com.example.testspringrestnormal.models.Adresse;
import com.example.testspringrestnormal.models.dto.AdresseSearch;
import com.example.testspringrestnormal.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class AdresseSpecification {

    public static Specification<Adresse> filter(AdresseSearch object) {
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
            
            System.out.println("🔍 TEMPLATE DEBUG: name=latitude | columnType=[float8] | isText=false | isNumeric=true");

            
            if(object.getLatitude()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("latitude"), object.getLatitude()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("latitude"), object.getLatitudeMin(), object.getLatitudeMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=codePostal | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("codePostal"), object.getCodePostal(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=ville | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("ville"), object.getVille(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=rue | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("rue"), object.getRue(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
