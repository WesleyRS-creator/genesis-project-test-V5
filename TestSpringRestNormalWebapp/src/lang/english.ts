const english = {
  messages: {
    projectName: 'testSpringRestNormal',
    common: {
          update: 'update',
          delete: 'delete',
          create: 'create',
          apply: 'Apply',
          go: 'go',
          views: 'views',
          view: 'view',
          details: 'details',
          list: 'list',
          new: 'new',
          actions: 'actions',
          back: 'back',
          layout: 'layout',
          theme: 'theme',
          general: 'general',
          settings: 'settings',
          entities: 'entities',
          entity: 'entity',
          filter: 'filter',
          rows: 'rows',
          showing: 'showing',
          previous: 'previous',
          next: 'next',
          page: 'page',
          add: 'add',
          description: 'description',
          return: 'return',
          home: 'home',
          login: 'login',
          logout: 'logout',
          register: 'register',
          profile: 'profile',
          username: 'username',
          email: 'email',
          password: 'password',
          language: 'language',
          confirm: 'confirm',
          cancel: 'cancel',
          no: 'no',
          save: 'Save',
          edit: 'Edit',
          read:'read'
        },
        box_confirm:
        {
          H1Se:"Are you sure you want to delete this item?",
          H2Se:"This cannot be undone"
        },
        header:
        {
          backList:"Back to list",
          list:"List",
          details:"Details",
          add:"Add new",
          form:"Form"
        },
        banner:
        {
          lang: 'Language',
          disconnect:"logout"
        },
        saying_words:
        {
          entries:"new entries",
          details:"details",
          information:"information",
          and:"and",
          needs:"what you don't need"
        },
        navbar: {
          core: 'Core',
          entities: 'ENTITIES', 
          views: 'VIEWS',
          settings: 'Settings',
          home: 'Home',
          customisation: 'LAYOUT CUSTOMISATION',
        },

        authentication: {
          login: 'Login',
          logout: 'Logout',
          register: 'Register',
          profile: 'Profile',
          username: 'Username',
          email: 'Email',
          password: 'Password',
          confirmPassword: 'Confirm your @:common.password',
          passwordPlaceholder: '••••••••',

          title: 'Sign in',
          submit: 'Sign in',
          invalid: 'Invalid credentials',
          createAccount: "Create account",
          backToSignIn: "Back to sign in",
          dontHaveAccount: "You don't have an account?",
        },

        register: {
          title: "Create account",
          name: "Name",
          email: "Email",
          password: "Password",
          submit: "Register",
          backToSignIn: "Back to sign in",
          success: "Account created successfully",
          error: "Registration failed",
          haveAccount: 'You already have an account?',
        },
        home: {
          greet: 'Hello',
          greetDeveloper: '@.capitalize:home.greet Developer',
          welcome: 'Welcome on @.capitalize:projectName frontend app',
          simple_welcome:'Welcome',
          titles: {
            entities: 'List of @:common.entities',
            views: 'List of @:common.views',
          },
        },

        pagination: {
          sizeLabel: 'Showing:',
          previous: 'previous @:common.page',
          next: 'next @:common.page',
          goToLabel: 'Go to ',
          totalRows: ' of {total} @:common.rows',
          pageOf: 'Page {page} of {total}',
        },

        search: {
          simple_filter:"Filters",
          selection:"select a field",
          filters: ' No @:common.filter | filter | filter ({count})',
        },

        entity: {
          nav: '{entity} / ',
          list: {
            nav: 'List',
            filters: 'Filters',
            addFilter: 'Add filter',
            removeFilter: 'Remove {{label}} filter',
          },
          create: {
            nav: 'New',
            submitLabel: 'Create {entity}',
          },
          update: {
            nav: 'Update',
            submitLabel: 'Update {entity}',
          },
          details: {
            nav: 'Details',
          },
        },

        button: {
          addEntity: 'Add @:common.new {entity}',
          applySearch: 'Apply',
          backTo: 'Back to {name}',
          backToList: 'Back to @:common.list',
          backToListDescription: 'Return to the @:common.list @:common.view',
          go: 'Go',
          delete: 'Delete',
          update: 'Update',
          create: 'Create',
          cancel: 'Cancel',
          validate:'Submit',
          export: 'Export to CSV',
          back: 'Retrun',
        },

        settings: {
          layout: 'Layout',
          theme: 'Theme',
          general: 'General',
          language: 'Language',
          pageTitle: 'Settings',
          switchLayout: 'Switch Layout',
        },

        // Entity definitions (scalable for many entities)
        entities:{
          adresse: {
            name: 'adresse | adresses',
            nav: {
              title: '@.capitalize:entities.adresse.name',
              create: 'Create @.capitalize:entities.adresse.name',
              list: '@.capitalize:entities.adresse.name @:common.list',
            },
          },
          participer: {
            name: 'participer | participers',
            nav: {
              title: '@.capitalize:entities.participer.name',
              create: 'Create @.capitalize:entities.participer.name',
              list: '@.capitalize:entities.participer.name @:common.list',
            },
          },
          passeport: {
            name: 'passeport | passeports',
            nav: {
              title: '@.capitalize:entities.passeport.name',
              create: 'Create @.capitalize:entities.passeport.name',
              list: '@.capitalize:entities.passeport.name @:common.list',
            },
          },
          personne: {
            name: 'personne | personnes',
            nav: {
              title: '@.capitalize:entities.personne.name',
              create: 'Create @.capitalize:entities.personne.name',
              list: '@.capitalize:entities.personne.name @:common.list',
            },
          },
          projet: {
            name: 'projet | projets',
            nav: {
              title: '@.capitalize:entities.projet.name',
              create: 'Create @.capitalize:entities.projet.name',
              list: '@.capitalize:entities.projet.name @:common.list',
            },
          },
          
        },
        views: {
          vFicheCompletePersonne: {
            name: 'vFicheCompletePersonne | vFicheCompletePersonnes',
            nav: {
              title: '@.capitalize:views.vFicheCompletePersonne.name',
              list: '@.capitalize:views.vFicheCompletePersonne.name @:common.list',                  
            },
          },
          vProjetsEtEquipe: {
            name: 'vProjetsEtEquipe | vProjetsEtEquipes',
            nav: {
              title: '@.capitalize:views.vProjetsEtEquipe.name',
              list: '@.capitalize:views.vProjetsEtEquipe.name @:common.list',                  
            },
          },
          vSynthetiseGlobal: {
            name: 'vSynthetiseGlobal | vSynthetiseGlobals',
            nav: {
              title: '@.capitalize:views.vSynthetiseGlobal.name',
              list: '@.capitalize:views.vSynthetiseGlobal.name @:common.list',                  
            },
          },
          
        },
        error: {
          error: 'Error',
          400: 'Bad request',
          403: 'Access denided',
          404: {
            title: 'Page not found',
            description: "Sorry, the page you are searching doesn't exist or moved to another url",
          },
          500: 'Error 500',
        },
        layout: {
          switchToHorizontal: 'Switch to horizontal',
          switchToVertical: 'Switch to vertical',
        },
        theme: {
          switchToLight: 'Light mode',
          switchToDark: 'Dark mode'
        },
        language: {
          choose: 'Language',
          en: 'English',
          fr: 'French',
          es: 'Spanish',
          de: 'German',
          it: 'Italian',
          pt: 'Portuguese',
          'pt-BR': 'Portuguese (Brazil)',
          zh: 'Chinese',
          ja: 'Japanese',
        },
        state: {
          loading: 'Loading…',
          notFound: 'Not found',
          success: 'Success',
          creating: 'Creating...',
          updating: 'Updating...',
        },
        confirmation: {
          delete: 'Confirm deletion?',
        },
        navigation: {
          list: 'List',
          add: 'Add',
        },
        profile: {
          myProfile: 'My Profile',
          edit: 'Edit Profile',
          changePassword: 'Change Password',
          oldPassword: 'Current Password',
          newPassword: 'New Password',
          confirmPassword: 'Confirm Password',
          updateSuccess: 'Profile updated successfully',
          updateError: 'Failed to update profile',
          usernameChangedLogout: 'Username changed. You will be logged out to sign in with your new credentials.',
          usernameChangeWarning: '⚠️ Warning: Changing your username will log you out. You will need to sign in with your new credentials.',
          passwordChangeSuccess: 'Password changed successfully',
          passwordChangeError: 'Failed to change password',
          passwordMismatch: 'Passwords do not match',
        },
      },

      dateTimeFormats: {
        short: { year: 'numeric', month: 'short', day: 'numeric' },
        long: { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' },
        time: { hour: '2-digit', minute: '2-digit' },
        full: {
          year: 'numeric',
          month: 'long',
          day: 'numeric',
          weekday: 'long',
          hour: '2-digit',
          minute: '2-digit',
        },
      },
      numberFormats: {
        currency: {
          style: 'currency',
          currency: 'USD',
          currencyDisplay: 'symbol',
        },
        decimal: {
          style: 'decimal',
          minimumFractionDigits: 2,
          maximumFractionDigits: 2,
        },
        percent: {
          style: 'percent',
          useGrouping: true,
        },
      },
    } as const
    export default english
