class ApplicationController < ActionController::API

    before_action :authenticate_request
    attr_reader :current_user

    #Home page
    def index
        render json: {status: 'SUCCESS', message: 'DPIC - API'}, status: :ok
    end


    private

    # Pour utiliser le token, il doit y avoir une méthode current_user qui «persistera» l'utilisateur. Afin que current_user soit disponible pour tous les contrôleurs
    def authenticate_request
        @current_user = AuthorizeApiRequest.call(request.headers).result
        render json: { error: 'Not Authorized' }, status: 401 unless @current_user
    end
    
end
