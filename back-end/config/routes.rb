Rails.application.routes.draw do


  ##########################                    "PARTIE FRONT-END"             #####################

  #routes pour l'Administrateur
  get 'admins' => 'administrateurs#index'
  get 'admin/:id' => 'administrateurs#show'
  post 'newAdmin' => 'administrateurs#create'
  put 'updateAdmin/:id' => 'administrateurs#update'
  put 'deleteAdmin/:id' => 'administrateurs#delete'


  #routes pour le sondage
  get 'sondages' => 'sondages#index'
  get 'sondage/:id' => 'sondages#show'
  post 'newSondage' => 'sondages#create'
  put 'updateSondage/:id' => 'sondages#update'
  put 'publierSondage/:id' => 'sondages#publierSondage'
  put 'activerResultats/:id' => 'sondages#activerResultats'
  put 'deleteSondage/:id' => 'sondages#delete'


  #routes pour l'utilisateur
  get 'utilisateurs' => 'utilisateurs#index'
  get 'utilisateur/:id' => 'utilisateurs#show'
  post 'newUtilisateur' => 'utilisateurs#create'
  put 'updateUtilisateur/:id' => 'utilisateurs#update'
  put 'deleteUtilisateur/:id' => 'utilisateurs#delete'


  #routes pour participer
  get 'participations' => 'participers#index'
  get 'participationBySondage/:id' => 'participers#showParticipationBySondage'
  get 'participationByUserAndSondage/:idUser/:idSondage' => 'participers#showParticipationByUserAndSondage'
  get 'participationByQuestionAndSondage/:idQuestion/:idSondage' => 'participers#showParticipationByQuestionAndSondage'
  #post 'newParticipation' => 'participers#create'
  put 'updateParticipation/:idUser/:idSondage/:idQuestion' => 'participers#update'
  put 'deleteParticipation/:idUser/:idSondage/:idQuestion' => 'participers#delete'


  #routes pour question
  get 'questions' => 'questions#index'
  get 'question/:id' => 'questions#show'
  get 'questionsBySondage/:id' => 'questions#showBySondage'


  #routes pour question ouverte
  get 'questionsOuvertes' => 'questionouvertes#index'
  get 'questionOuverte/:id' => 'questionouvertes#show'
  post 'newQuestionOuverte' => 'questionouvertes#create'
  put 'updateQuestionOuverte/:id' => 'questionouvertes#update'
  put 'deleteQuestionOuverte/:id' => 'questionouvertes#delete'


  #routes pour question choix
  get 'questionsChoix' => 'questionchoixes#index'
  get 'questionChoix/:id' => 'questionchoixes#show'
  post 'newQuestionChoix' => 'questionchoixes#create'
  put 'updateQuestionChoix/:id' => 'questionchoixes#update'
  put 'deleteQuestionChoix/:id' => 'questionchoixes#delete'


  #routes pour question points
  get 'questionsPoints' => 'questionpoints#index'
  get 'questionPoints/:id' => 'questionpoints#show'
  post 'newQuestionPoints' => 'questionpoints#create'
  put 'updateQuestionPoints/:id' => 'questionpoints#update'
  put 'deleteQuestionPoints/:id' => 'questionpoints#delete'

  #routes pour groupe de questions
  get 'groupesQuestions' => 'groupequestions#index'
  get 'groupeQuestions/:id' => 'groupequestions#show'
  post 'newGroupeQuestions' => 'groupequestions#create'
  put 'updateGroupeQuestions/:id' => 'groupequestions#update'
  put 'deleteGroupeQuestions/:id' => 'groupequestions#delete'
##########################                    "END PARTIE FRONT-END"             #####################


##########################                    "PARTIE MOBILE"                    #####################
  get 'sondagesPublies' => 'sondages#showSondagesPublies' # Fournir la listes des sondages publies
  get 'sondagePublie/:idSondage' => 'sondages#showSondagePublie' # Fournir un sondage donné par son id => id du sondage
  post 'repondre' => 'participers#repondreSondagePublie' # Le post contient les éléments suivants => id_utilisateur, id_sondage, id_question, reponse
  get 'questionsDuSondage/:idSondage' => 'questions#questionsDuSondage' # Afficher les questions d'un sondage publié par l'id du sondage
  get 'questionDuSondage/:idSondage/:idQuestion' => 'questions#questionDuSondage' # Afficher une question d'un sondage publié par l'id du sondage et l'id question
##########################                    "END PARTIE MOBILE"                #####################


  #route home page
  get '/' => 'application#index'
  #no matches route
  get '*path' => redirect('/')

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
