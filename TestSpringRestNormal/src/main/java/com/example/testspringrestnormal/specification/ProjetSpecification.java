package com.example.testspringrestnormal.specification;

import com.example.testspringrestnormal.models.Projet;
import com.example.testspringrestnormal.models.dto.ProjetSearch;
import com.example.testspringrestnormal.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ProjetSpecification {

    public static Specification<Projet> filter(ProjetSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            System.out.println("🔍 TEMPLATE DEBUG: name=budgetEstime | columnType=[float8] | isText=false | isNumeric=true");

            
            if(object.getBudgetEstime()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("budgetEstime"), object.getBudgetEstime()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("budgetEstime"), object.getBudgetEstimeMin(), object.getBudgetEstimeMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=dateDebut | columnType=[date] | isText=false | isNumeric=false");

            
            if(object.getDateDebut()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("dateDebut"), object.getDateDebut()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("dateDebut"), object.getDateDebutMin(), object.getDateDebutMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=id | columnType=[int4] | isText=false | isNumeric=true");

            
            if(object.getId()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("id"), object.getId()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=nomProjet | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("nomProjet"), object.getNomProjet(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
