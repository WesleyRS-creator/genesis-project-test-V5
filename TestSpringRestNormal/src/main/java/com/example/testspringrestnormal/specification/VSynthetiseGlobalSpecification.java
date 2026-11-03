package com.example.testspringrestnormal.specification;

import com.example.testspringrestnormal.models.VSynthetiseGlobal;
import com.example.testspringrestnormal.models.dto.VSynthetiseGlobalSearch;
import com.example.testspringrestnormal.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class VSynthetiseGlobalSpecification {

    public static Specification<VSynthetiseGlobal> filter(VSynthetiseGlobalSearch object) {
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
            
            System.out.println("🔍 TEMPLATE DEBUG: name=nomComplet | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("nomComplet"), object.getNomComplet(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=trancheAge | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("trancheAge"), object.getTrancheAge(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=totalParticipations | columnType=[int8] | isText=false | isNumeric=true");

            
            if(object.getTotalParticipations()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("totalParticipations"), object.getTotalParticipations()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("totalParticipations"), object.getTotalParticipationsMin(), object.getTotalParticipationsMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=rolePrincipal | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("rolePrincipal"), object.getRolePrincipal(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=budgetMoyenProjets | columnType=[numeric] | isText=false | isNumeric=true");

            
            if(object.getBudgetMoyenProjets()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("budgetMoyenProjets"), object.getBudgetMoyenProjets()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("budgetMoyenProjets"), object.getBudgetMoyenProjetsMin(), object.getBudgetMoyenProjetsMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=budgetGlobalMoyen | columnType=[numeric] | isText=false | isNumeric=true");

            
            if(object.getBudgetGlobalMoyen()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("budgetGlobalMoyen"), object.getBudgetGlobalMoyen()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("budgetGlobalMoyen"), object.getBudgetGlobalMoyenMin(), object.getBudgetGlobalMoyenMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=positionBudget | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("positionBudget"), object.getPositionBudget(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=nombreVilles | columnType=[int8] | isText=false | isNumeric=true");

            
            if(object.getNombreVilles()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("nombreVilles"), object.getNombreVilles()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("nombreVilles"), object.getNombreVillesMin(), object.getNombreVillesMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=latitudeMoyenne | columnType=[numeric] | isText=false | isNumeric=true");

            
            if(object.getLatitudeMoyenne()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("latitudeMoyenne"), object.getLatitudeMoyenne()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("latitudeMoyenne"), object.getLatitudeMoyenneMin(), object.getLatitudeMoyenneMax(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
