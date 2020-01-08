Rails.application.routes.draw do


  # Pour supprimer un enregistrement dans la base de données, donner la valeur true pour l'attribut etat => {"etat": true} 

  ##########################                    "ROUTES PARTIE FRONT-END"             #####################
  #routes Administrateur
  get 'admins' => 'administrateurs#index' # Fournir la listes des admins
  get 'admin/:id' => 'administrateurs#show' # Fournir un admin par son id => id_admin
  post 'newAdmin' => 'administrateurs#create' # Ajouter un nouveau admin | éléments à fournir: {pseudo_administrateur, email_administrateur, motDePasse_administrateur}
  put 'updateAdmin/:id' => 'administrateurs#update' # Modifier un admin par son id => id_admin
  put 'deleteAdmin/:id' => 'administrateurs#delete' # Supprimer un admin par son id => id_admin
  post 'checkAdminPassword' => 'administrateurs#checkAdminPassword' # Verifier le mot de passe d'un admin par son id_administrateur. Util dans le cas de modification du mot de passe | éléments à fournir:  {d_administrateur, motDePasse_administrateur} => motDePasse_administrateur en MD5
  post 'loginAdmin' => 'administrateurs#loginAdmin' # Verifier le login d'un admin | éléments à fournir: {email_administrateur, motDePasse_administrateur} => motDePasse_administrateur en MD5

  #routes Sondage
  get 'sondages' => 'sondages#index' # Fournir la listes des sondages
  get 'sondage/:id' => 'sondages#show' # Fournir un sondage par son id => id_sondage
  post 'newSondage' => 'sondages#create' # Ajouter un nouveau sondage | éléments à fournir: {intituleSondage, descriptionSondage, administrateur_id}
  put 'updateSondage/:id' => 'sondages#update' # Modifier un sondage par son id_sondage | éléments à fournir: {intituleSondage, descriptionSondage, publier, resultats}
  put 'publierSondage/:id' => 'sondages#publierSondage' # Publier un sondage par son id => id_sondage afin de le mettre visible pour les smartphone
  put 'activerResultats/:id' => 'sondages#activerResultats' # Activer/Desactiver l'option resultats d'un sondage par son id => id_sondage. Si l'option est activée, l'utilisateur "Mobile" à le droit de voir les résultats de ce sondage
  put 'deleteSondage/:id' => 'sondages#delete' # Supprimer un sondage par son id => id_sondage


  #routes Utilisateur
  get 'utilisateurs' => 'utilisateurs#index' # Fournir la listes des utilisateurs
  get 'utilisateur/:id' => 'utilisateurs#show' # Fournir un user par son id => id_utilisateur
  post 'newUtilisateur' => 'utilisateurs#create' # Ajouter un nouveau user | éléments à fournir: {email, adresseIp}
  put 'updateUtilisateur/:id' => 'utilisateurs#update' # Modifier un user par son id => id_utilisateur | éléments à fournir: {email, adresseIp}
  put 'deleteUtilisateur/:id' => 'utilisateurs#delete' # Supprimer un user par son id => id_utilisateur


  #routes Participer
  get 'participations' => 'participers#index' # Fournir la listes de toutes les participations
  get 'participationsBySondage/:id' => 'participers#showParticipationBySondage' # Fournir la listes des participations pour un sondage donné par son id => id_sondage
  get 'participationsByUserAndSondage/:idUser/:idSondage' => 'participers#showParticipationByUserAndSondage' # Fournir la listes des participations d'un user(id_utilisateur) pour un sondage donné par son id => id_sondage
  get 'participationsByQuestionAndSondage/:idQuestion/:idSondage' => 'participers#showParticipationByQuestionAndSondage' # Fournir la listes des participations pour une question donnée(id_question) pour un sondage donné par son id => id_sondage
  #post 'newParticipation' => 'participers#create' # Ajouter une participation par un admin
  #put 'updateParticipation/:idUser/:idSondage/:idQuestion' => 'participers#update' # Modifier une participation
  put 'deleteParticipation/:idUser/:idSondage/:idQuestion' => 'participers#delete' # Supprimer une participation


  #routes Question
  get 'questions' => 'questions#index' # Fournir la liste de toutes les questions
  get 'question/:id' => 'questions#show' # Fournir une question par son id => id_question
  get 'questionsBySondage/:id' => 'questions#showBySondage' # Fourinr les questions d'un sondage par son id => id_sondage


  #routes Question ouverte
  get 'questionsOuvertes' => 'questionouvertes#index' # Fournir la liste de toutes les questions de type QuestionOuverte
  get 'questionOuverte/:id' => 'questionouvertes#show' # Fournir une question ouverte par son id => id_question
  post 'newQuestionOuverte' => 'questionouvertes#create' # Ajouter un nvlle question ouverte | éléments à fournir: {intitule, estObligatoire, nombreDeCaractere, ordre, sondage_id}
  put 'updateQuestionOuverte/:id' => 'questionouvertes#update' # Modifier une question ouverte par son id => id_question
  put 'deleteQuestionOuverte/:id' => 'questionouvertes#delete' # Supprimer une question ouverte par son id => id_question


  #routes Question choix
  get 'questionsChoix' => 'questionchoixes#index'
  get 'questionChoix/:id' => 'questionchoixes#show'
  post 'newQuestionChoix' => 'questionchoixes#create' # éléments à fournir: {intitule, estObligatoire, estUnique, lesChoix, ordre, sondage_id}
  put 'updateQuestionChoix/:id' => 'questionchoixes#update'
  put 'deleteQuestionChoix/:id' => 'questionchoixes#delete'


  #routes Question points
  get 'questionsPoints' => 'questionpoints#index'
  get 'questionPoints/:id' => 'questionpoints#show'
  post 'newQuestionPoints' => 'questionpoints#create' # éléments à fournir: {intitule, estObligatoire, minPoints, maxPoints, ordre, sondage_id}
  put 'updateQuestionPoints/:id' => 'questionpoints#update'
  put 'deleteQuestionPoints/:id' => 'questionpoints#delete'

  #routes Groupe de questions
  get 'groupesQuestions' => 'groupequestions#index'
  get 'groupeQuestions/:id' => 'groupequestions#show'
  post 'newGroupeQuestions' => 'groupequestions#create' # éléments à fournir: {intitule, estObligatoire, numerosDeQuestionsGroupe, ordre, sondage_id}
  put 'updateGroupeQuestions/:id' => 'groupequestions#update'
  put 'deleteGroupeQuestions/:id' => 'groupequestions#delete'
##########################                    "END PARTIE FRONT-END"             #####################


##########################                    "ROUTES PARTIE MOBILE"                    #####################
  post 'newUser' => 'utilisateurs#create' # Creer un utilisateur afin de repondre aux sondages publiés | éléments à fournir: {email, adresseIp}
  get 'sondagesPublies' => 'sondages#showSondagesPublies' # Fournir la listes des sondages publies
  get 'sondagePublie/:idSondage' => 'sondages#showSondagePublie' # Fournir un sondage donné par son id => id du sondage
  post 'repondre' => 'participers#repondreSondagePublie' # Le post contient les éléments suivants: {utilisateur_id, sondage_id, question_id, reponse}
  get 'questionsDuSondage/:idSondage' => 'questions#questionsDuSondage' # Afficher les questions d'un sondage publié par l'id du sondage
  get 'questionDuSondage/:idSondage/:idQuestion' => 'questions#questionDuSondage' # Afficher une question d'un sondage publié par l'id du sondage et l'id question
##########################                    "END ROUTES PARTIE MOBILE"                #####################


  #route Home page
  get '/' => 'application#index'
  #no matches route
  get '*path' => redirect('/')

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
