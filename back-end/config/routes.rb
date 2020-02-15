Rails.application.routes.draw do


  ##########################                    "ROUTES PARTIE FRONT-END"             #####################
  #routes Administrateur
  get 'admins' => 'administrateurs#index' # Fournir la liste des admins
  get 'admin/:id' => 'administrateurs#show' # Fournir un admin par son id => id_admin
  post 'new/admin' => 'administrateurs#create' # Ajouter un nouveau admin | éléments à fournir: {pseudo_administrateur, email_administrateur, motDePasse_administrateur}
  put 'update/admin/:id' => 'administrateurs#update' # Modifier un admin par son id => id_admin
  put 'delete/admin/:id' => 'administrateurs#delete' # Supprimer un admin par son id => id_admin
  post 'checkPassword/admin/:id' => 'administrateurs#checkAdminPassword' # Verifier le mot de passe d'un admin par son id_administrateur. Util dans le cas de modification du mot de passe | éléments à fournir:  {d_administrateur, motDePasse_administrateur} => motDePasse_administrateur en MD5
  post 'checkEmail/admin' => 'administrateurs#checkAdminEmail' # Verifier l'email d'un admin | éléments à fournir:  {email_administrateur}
  post 'login/admin' => 'administrateurs#loginAdmin' # Verifier le login d'un admin | éléments à fournir: {email_administrateur, motDePasse_administrateur} => motDePasse_administrateur en MD5


  #routes Categorie
  get 'categories' => 'categories#index' # Fournir la liste des categories
  get 'categorie/:id' => 'categories#show' # Fournir une categorie par son id => id_categorie
  post 'new/categorie' => 'categories#create' # éléments à fournir: {intitule}
  put 'update/categorie/:id' => 'categories#update' # Modifier une categorie par son id => id_categorie
  put 'delete/categorie/:id' => 'categories#delete' # Supprimer une categorie par son id => id_categorie


  #routes Sondage
  get 'sondages' => 'sondages#index' # Fournir la liste des sondages
  get 'sondages/categorie/:id' => 'sondages#sondagesParCategorie' # Fournir la liste des sondages par categorie
  get 'sondages/admin/:id' => 'sondages#sondagesParAdmin' # Fournir la liste des sondages créés par un admin
  get 'questions/sondage/:idSondage' => 'questions#questionsParSondage' # Afficher les questions d'un sondage par l'id du sondage
  get 'sondage/:id' => 'sondages#show' # Fournir un sondage par son id => id_sondage
  post 'new/sondage' => 'sondages#create' # Ajouter un nouveau sondage | éléments à fournir: {intituleSondage, descriptionSondage, administrateur_id}
  put 'updateSondage/:id' => 'sondages#update' # Modifier un sondage par son id_sondage | éléments à fournir: {intituleSondage, descriptionSondage, publier, resultats}
  put 'publierSondage/:id' => 'sondages#publierSondage' # Publier un sondage par son id => id_sondage afin de le mettre visible pour les smartphone
  put 'activerResultats/:id' => 'sondages#activerResultats' # Activer/Desactiver l'option resultats d'un sondage par son id => id_sondage. Si l'option est activée, l'utilisateur "Mobile" à le droit de voir les résultats de ce sondage
  put 'deleteSondage/:id' => 'sondages#delete' # Supprimer un sondage par son id => id_sondage


  #routes Utilisateur
  get 'utilisateurs' => 'utilisateurs#index' # Fournir la liste des utilisateurs
  get 'utilisateur/:id' => 'utilisateurs#show' # Fournir un user par son id => id_utilisateur
  post 'new/utilisateur' => 'utilisateurs#create' # Ajouter un nouveau user | éléments à fournir: {email, adresseIp}
  put 'update/utilisateur/:id' => 'utilisateurs#update' # Modifier un user par son id => id_utilisateur | éléments à fournir: {email, adresseIp}
  put 'delete/tilisateur/:id' => 'utilisateurs#delete' # Supprimer un user par son id => id_utilisateur


  #routes Participer
  get 'participations' => 'participers#index' # Fournir la liste de toutes les participations
  get 'participations/Sondage/:id' => 'participers#showParticipationsBySondage' # Fournir la liste des participations pour un sondage donné par son id => id_sondage
  get 'participations/user/sondage/:idUser/:idSondage' => 'participers#showParticipationsByUserAndSondage' # Fournir la liste des participations d'un user(id_utilisateur) pour un sondage donné par son id => id_sondage
  get 'participations/question/sondage/:idQuestion/:idSondage' => 'participers#showParticipationsByQuestionAndSondage' # Fournir la liste des participations pour une question donnée(id_question) pour un sondage donné par son id => id_sondage
  #post 'new/participation' => 'participers#create' # Ajouter une participation par un admin
  #put 'update/participation/:idUser/:idSondage/:idQuestion' => 'participers#update' # Modifier une participation
  put 'delete/participation/:idUser/:idSondage/:idQuestion' => 'participers#delete' # Supprimer une participation


  #routes Question
  get 'questions' => 'questions#index' # Fournir la liste de toutes les questions
  get 'question/:id' => 'questions#show' # Fournir une question par son id => id_question
  get 'questions/sondage/:id' => 'questions#showBySondage' # Fourinr les questions d'un sondage par son id => id_sondage


  #routes de questions de type: QuestionOuverte
  get 'questionsOuvertes' => 'questionouvertes#index' # Fournir la liste de toutes les questions de type QuestionOuverte
  get 'questionOuverte/:id' => 'questionouvertes#show' # Fournir une question ouverte par son id => id_question
  post 'new/questionOuverte' => 'questionouvertes#create' # Ajouter un nvlle question ouverte | éléments à fournir: {intitule, estObligatoire, nombreDeCaractere, ordre, sondage_id}
  put 'update/questionOuverte/:id' => 'questionouvertes#update' # Modifier une question ouverte par son id => id_question
  put 'delete/questionOuverte/:id' => 'questionouvertes#delete' # Supprimer une question ouverte par son id => id_question


  #routes de questions de type: QuestionChoix
  get 'questionsChoix' => 'questionchoixes#index' # Fournir la liste de toutes les questions de type QuestionChoix
  get 'questionChoix/:id' => 'questionchoixes#show' # Fournir une questionChoix par son id => id_question
  post 'new/questionChoix' => 'questionchoixes#create' # éléments à fournir: {intitule, estObligatoire, estUnique, ordre, sondage_id}
  put 'update/questionChoix/:id' => 'questionchoixes#update' # éléments à fournir {intitule, estObligatoire, estUnique, ordre}
  put 'delete/questionChoix/:id' => 'questionchoixes#delete' # Supprimer une questionChoix par son id => id_question


  #routes de questions de type: QuestionPoints
  get 'questionsPoints' => 'questionpoints#index' # Fournir la liste de toutes les questions de type QuestionPoints
  get 'questionPoints/:id' => 'questionpoints#show' # Fournir une QuestionPoints par son id => id_question
  post 'new/questionPoints' => 'questionpoints#create' # éléments à fournir: {intitule, estObligatoire, minPoints, maxPoints, ordre, sondage_id}
  put 'update/questionPoints/:id' => 'questionpoints#update' # éléments à fournir {intitule, estObligatoire, minPoints, maxPoints, ordre}
  put 'delete/questionPoints/:id' => 'questionpoints#delete' # Supprimer une questionPoints par son id => id_question

  #routes de questions de type: QuestionGroupe
  get 'groupesQuestions' => 'groupequestions#index' # Fourinr tout les questions de type GroupeQuestion
  get 'groupeQuestions/:id' => 'groupequestions#show' # Fournir un GroupeQuestion par id_question
  post 'new/qroupeQuestions' => 'groupequestions#create' # éléments à fournir: {intitule, estObligatoire, ordre, sondage_id}
  put 'update/groupeQuestions/:id' => 'groupequestions#update' # éléments à fournir {intitule, estObligatoire, ordre}
  put 'delete/groupeQuestions/:id' => 'groupequestions#delete' # Supprimer une QuestionGroupe par son id => id_question

  #route Groupe qui contient les IDs des questions d'une question de type QuestionGroupe
  get 'questions/groupe/:id' => 'groupes#show' # Fournir les questions d'un groupe | :id => est l'id du question de type GroupeQuestion
  post 'addQuestion/groupe' => 'groupes#create' # éléments à fournir: {id_groupe, id_question} | id_groupe => est l'id du question de type GroupeQuestion
  put 'delete/groupe/:id' => 'groupes#delete' # Supprimer un groupe par son id => id_groupe

  # route Résultat
  get 'resultats/:idSondage' => 'resultats#result'

  #routes Choix
  get 'choix' => 'choixes#index' # Fournir la liste des choix
  get 'choix/:id' => 'choixes#show' # Fournir un choix par son id => id_choix
  get 'choix/question/:id' => 'choixes#afficherLesChoixParQuestion' # Fournir la liste des choix pour une question donnée par son id => id_question
  post 'new/choix' => 'choixes#create' # éléments à fournir: {intituleChoix, question_id}
  put 'update/choix/:id' => 'choixes#update' # Modifier un choix par son id => id_choix
  put 'delete/choix/:id' => 'choixes#delete' # Supprimer un choix par son id => id_choix

##########################                    "END PARTIE FRONT-END"             #####################


##########################                    "ROUTES PARTIE MOBILE"                    #####################
  post 'newUser' => 'utilisateurs#create' # Creer un utilisateur afin de repondre aux sondages publiés | éléments à fournir: {email, adresseIp}
  get 'sondagesPublies' => 'sondages#showSondagesPublies' # Fournir la liste des sondages publies
  get 'sondagesPublies/categorie/:id' => 'sondages#showSondagesPubliesByCategorie' # Fournir la liste des sondages publies par categorie
  get 'sondagePublie/:idSondage' => 'sondages#showSondagePublie' # Fournir un sondage donné par son id => id du sondage
  post 'repondre' => 'participers#repondreSondagePublie' # Le post contient les éléments suivants: {utilisateur_id, sondage_id, question_id, reponse}
  get 'questionsDuSondage/:idSondage' => 'questions#questionsDuSondage' # Afficher les questions d'un sondage publié par l'id du sondage
  get 'questionDuSondage/:idSondage/:idQuestion' => 'questions#questionDuSondage' # Afficher une question d'un sondage publié par l'id du sondage et l'id question
  get 'questionsDuGroupe/:id' => 'groupes#show' # Fournir les questions d'un groupe | :id => est l'id du question de type GroupeQuestion
  get 'lesChoixParQuestion/:idSondage/:idQuestion' => 'choixes#afficherLesChoixParQuestionPublie' # Fournir la liste des choix pour une question donnée par son idQuestion => id_question | et idSondage => id_sondage
  get 'lesChoixParSondage/:idSondage' => 'choixes#afficherLesChoixParSondagePublie' # Fournir la liste des choix d'un sondage par son idSondage => id_sondage
  get 'questionsPublies' => 'questions#listeDesQuestionsPublies' # Fournir la liste de toutes les questions publies
  get 'choixPublies' => 'choixes#listeDesChoixPublies' # Fournir la liste de tout les choix publies
  get 'checkResultats/:idSondage' => 'sondages#checkResultats' # Vérifier si les résultats d'un sondage sont disponible
  get 'resultatsPublie/:idSondage' => 'resultats#resultatsPublie' # Fournir les résultats d'un sondage publié
##########################                    "END ROUTES PARTIE MOBILE"                #####################


  #route Home page
  get '/' => 'application#index'
  #no matches route
  get '*path' => redirect('/')

  #route AuthApi
  post 'authenticate' => 'apiauthentications#authenticate' # Permet d'authentifier afin d'avoir l'acces à l'API | éléments à fournir: {email, password}

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end