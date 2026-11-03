package com.example.testspringrestnormal.specification;

import com.example.testspringrestnormal.models.VProjetsEtEquipe;
import com.example.testspringrestnormal.models.dto.VProjetsEtEquipeSearch;
import com.example.testspringrestnormal.utils.WebUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class VProjetsEtEquipeSpecification {

    public static Specification<VProjetsEtEquipe> filter(VProjetsEtEquipeSearch object) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            System.out.println("🔍 TEMPLATE DEBUG: name=idProjet | columnType=[int4] | isText=false | isNumeric=true");

            
            if(object.getIdProjet()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("idProjet"), object.getIdProjet()
                    )
                );
            }
            
            System.out.println("🔍 TEMPLATE DEBUG: name=nomProjet | columnType=[varchar] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("nomProjet"), object.getNomProjet(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=dateDebut | columnType=[date] | isText=false | isNumeric=false");

            
            if(object.getDateDebut()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("dateDebut"), object.getDateDebut()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("dateDebut"), object.getDateDebutMin(), object.getDateDebutMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=budgetEstime | columnType=[float8] | isText=false | isNumeric=true");

            
            if(object.getBudgetEstime()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("budgetEstime"), object.getBudgetEstime()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("budgetEstime"), object.getBudgetEstimeMin(), object.getBudgetEstimeMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=dureeJours | columnType=[int4] | isText=false | isNumeric=true");

            
            if(object.getDureeJours()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("dureeJours"), object.getDureeJours()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("dureeJours"), object.getDureeJoursMin(), object.getDureeJoursMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=nombreParticipants | columnType=[int8] | isText=false | isNumeric=true");

            
            if(object.getNombreParticipants()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("nombreParticipants"), object.getNombreParticipants()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("nombreParticipants"), object.getNombreParticipantsMin(), object.getNombreParticipantsMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=budgetParParticipant | columnType=[numeric] | isText=false | isNumeric=true");

            
            if(object.getBudgetParParticipant()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("budgetParParticipant"), object.getBudgetParParticipant()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("budgetParParticipant"), object.getBudgetParParticipantMin(), object.getBudgetParParticipantMax(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=listeEquipe | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("listeEquipe"), object.getListeEquipe(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=rolesDistribues | columnType=[text] | isText=true | isNumeric=false");

            
            WebUtils.addLikeIfPresent(criteriaBuilder, root.get("rolesDistribues"), object.getRolesDistribues(), predicates);
            
            System.out.println("🔍 TEMPLATE DEBUG: name=derniereInscription | columnType=[timestamp] | isText=false | isNumeric=false");

            
            if(object.getDerniereInscription()!=null){
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("derniereInscription"), object.getDerniereInscription()
                    )
                );
            }
            WebUtils.addInterval(criteriaBuilder, root.get("derniereInscription"), object.getDerniereInscriptionMin(), object.getDerniereInscriptionMax(), predicates);
            
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
