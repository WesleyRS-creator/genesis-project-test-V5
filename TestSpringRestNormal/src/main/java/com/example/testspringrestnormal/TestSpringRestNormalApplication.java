package com.example.testspringrestnormal;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info; 
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;




@EnableScheduling
@SpringBootApplication
public class TestSpringRestNormalApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestSpringRestNormalApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                    .info(new Info().title("testSpringRestNormal")
                    .description("Ce projet vise à développer une plateforme SaaS enterprise de gestion de ressources et de planification de projets, conçue pour optimiser la collaboration inter-équipes au sein des grandes organisations internationales. L'application offrira un tableau de bord centralisé et intuitif, permettant aux chefs de projet de suivre en temps réel l'avancement des tâches, d'allouer les ressources humaines et matérielles de manière optimale, et de générer des rapports analytiques détaillés et exportables. Les fonctionnalités principales incluent la gestion agile des workflows complexes, l'intégration native et sécurisée avec des outils de communication tiers (tels que Slack, Microsoft Teams et Jira), ainsi qu'un module avancé de contrôle d'accès basé sur les rôles (RBAC) pour garantir la confidentialité absolue des données sensibles. L'architecture technique repose sur une approche microservices moderne, assurant une haute disponibilité, une scalabilité horizontale sans friction et une maintenance simplifiée. L'interface utilisateur sera développée avec un framework frontend réactif pour garantir une expérience fluide, responsive et accessible sur tous les appareils. Ce système permettra aux organisations de réduire leurs délais de livraison de 30 %, d'améliorer significativement la transparence opérationnelle globale, et de s'adapter facilement aux futures évolutions métier ainsi qu'aux normes de conformité réglementaire en vigueur.")
                    .version("0.0.1-SNAPSHOT"));
    }
}
