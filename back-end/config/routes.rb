Rails.application.routes.draw do

  get 'admins' => 'administrateur#index'
  get 'admin/:id' => 'administrateur#show'
  post 'newAdmin' => 'administrateur#create'
  put 'updateAdmin/:id' => 'administrateur#update'
  put 'deleteAdmin/:id' => 'administrateur#delete'

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
