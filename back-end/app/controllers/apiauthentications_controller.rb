# L'action 'authenticate' prendra les paramètres JSON pour l'email et le mot de passe via le hachage des paramètres et les transmettra à la commande 'AuthenticateUser'.
# Si la commande réussit, elle renverra le token JWT à l'utilisateur

# Comment utiliser authenticate en ligne de commande:
# curl -H "Content-Type: application/json" -X POST -d '{"email":"example@mail.com","password":"exemple"}' http://localhost:port/authenticate
# une fois cette commade est lancée, un token a été généré sous format :
# {"auth_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE0NjA2NTgxODZ9.xsSwcPC22IR71OBv6bU_OGCSyfE89DvEzWfDU0iybAZ"}
# utilser ce token pour obtenir par exemple une liste des items:
# curl -H "Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE0NjA2NTgxODZ9.xsSwcPC22IR71OBv6bU_OGCSyfE89DvEzWfDU0iybAZ" http://localhost:port/items
# afin de changer le délai d'expiration du token consulter: /lib/json_web_token.rb


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
