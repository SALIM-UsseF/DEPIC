Rails.application.routes.draw do

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
  put 'deleteSondage/:id' => 'sondages#delete'


  #routes pour l'utilisateur
  get 'utilisateur' => 'utilisateurs#index'
  get 'utilisateur/:id' => 'utilisateurs#show'
  post 'newUtilisateur' => 'utilisateurs#create'
  put 'updateUtilisateur/:id' => 'utilisateurs#update'
  put 'deleteUtilisateur/:id' => 'utilisateurs#delete'


  #routes pour participer
  get 'participer' => 'participers#index'
  get 'participer/:id' => 'participers#show'
  post 'newParticiper' => 'participers#create'
  put 'updateParticiper/:id' => 'participers#update'
  put 'deleteParticiper/:id' => 'participers#delete'

  #routes pour question
  get 'question' => 'questions#index'
  get 'question/:id' => 'questions#show'
  post 'newQuestion' => 'questions#create'
  put 'updateQuestion/:id' => 'questions#update'
  put 'deleteQuestion/:id' => 'questions#delete'


  #routes pour question ouverte
  get 'questionOuverte' => 'questionouvertes#index'
  get 'questionOuverte/:id' => 'questionouvertes#show'
  post 'newQuestionOuverte' => 'questionouvertes#create'
  put 'updateQuestionOuverte/:id' => 'questionouvertes#update'
  put 'deleteQuestionOuverte/:id' => 'questionouvertes#delete'


  #routes pour question choix
  get 'questionChoix' => 'questionchoixes#index'
  get 'questionChoix/:id' => 'questionchoixes#show'
  post 'newQuestionChoix' => 'questionchoixes#create'
  put 'updateQuestionChoix/:id' => 'questionchoixes#update'
  put 'deleteQuestionChoix/:id' => 'questionchoixes#delete'


  #routes pour question points
  get 'questionPoints' => 'questionpoints#index'
  get 'questionPoints/:id' => 'questionpoints#show'
  post 'newQuestionPoints' => 'questionpoints#create'
  put 'updateQuestionPoints/:id' => 'questionpoints#update'
  put 'deleteQuestionPoints/:id' => 'questionpoints#delete'

  #routes pour groupe de questions
  get 'groupeQuestions' => 'groupequestions#index'
  get 'groupeQuestions/:id' => 'groupequestions#show'
  post 'newGroupeQuestions' => 'groupequestions#create'
  put 'updateGroupeQuestions/:id' => 'groupequestions#update'
  put 'deleteGroupeQuestions/:id' => 'groupequestions#delete'

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
