# L'action 'authenticate' prendra les paramètres JSON pour l'email et le mot de passe via le hachage des paramètres et les transmettra à la commande 'AuthenticateUser'.
# Si la commande réussit, elle renverra le token JWT à l'utilisateur

class ApiauthenticationsController < ApplicationController

    skip_before_action :authenticate_request

    def authenticate
      command = AuthenticateApi.call(params[:email], params[:password])
   
      if command.success?
        render json: { auth_token: command.result }
      else
        render json: { error: command.errors }, status: :unauthorized
      end
    end

end
