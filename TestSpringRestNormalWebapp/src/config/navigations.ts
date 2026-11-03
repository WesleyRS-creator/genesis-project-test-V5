const navigations = [
  // ENTITIES NAVIGATION
  {
    icon: 'fas fa-layer-group',
    sectionName: 'navbar.entities',
    navChilds: [
      {
        navTitle:'entities.adresse.nav.title',
        navChilds:[
          {
            navTitle: 'entities.adresse.nav.list',
            navLink: '/adresses',
          },
          {
            navTitle: 'entities.adresse.nav.create',
            navLink: '/adresses/create',
          },
        ]
      },
      
      {
        navTitle:'entities.participer.nav.title',
        navChilds:[
          {
            navTitle: 'entities.participer.nav.list',
            navLink: '/participers',
          },
          {
            navTitle: 'entities.participer.nav.create',
            navLink: '/participers/create',
          },
        ]
      },
      
      {
        navTitle:'entities.passeport.nav.title',
        navChilds:[
          {
            navTitle: 'entities.passeport.nav.list',
            navLink: '/passeports',
          },
          {
            navTitle: 'entities.passeport.nav.create',
            navLink: '/passeports/create',
          },
        ]
      },
      
      {
        navTitle:'entities.personne.nav.title',
        navChilds:[
          {
            navTitle: 'entities.personne.nav.list',
            navLink: '/personnes',
          },
          {
            navTitle: 'entities.personne.nav.create',
            navLink: '/personnes/create',
          },
        ]
      },
      
      {
        navTitle:'entities.projet.nav.title',
        navChilds:[
          {
            navTitle: 'entities.projet.nav.list',
            navLink: '/projets',
          },
          {
            navTitle: 'entities.projet.nav.create',
            navLink: '/projets/create',
          },
        ]
      },
      
      
    ],
  },
  // VIEWS NAVIGATION
  {
    icon: 'fas fa-box',
    sectionName: 'navbar.views',
    navChilds: [
      {
        navTitle:'views.vFicheCompletePersonne.nav.title',
        navLink: '/vFicheCompletePersonnes',
      },
      
      {
        navTitle:'views.vProjetsEtEquipe.nav.title',
        navLink: '/vProjetsEtEquipes',
      },
      
      {
        navTitle:'views.vSynthetiseGlobal.nav.title',
        navLink: '/vSynthetiseGlobals',
      },
      
      
    ],
  },
]
export default navigations
