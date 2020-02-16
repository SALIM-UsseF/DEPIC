class AuthorizeApiRequest

    # SimpleCommand: un moyen facile de créer des services
    # Son rôle est similaire à celui d'un helper, mais il facilite plutôt la connexion entre le contrôleur et le modèle, plutôt que le contrôleur et la vue
    # De cette façon, nous pouvons raccourcir le code dans les modèles et les contrôleurs
    prepend SimpleCommand
  
    # c'est là que les paramètres sont pris lors de l'appel de la commande
    def initialize(headers = {})
      @headers = headers
    end
  
    # c'est là que le résultat est retourné
    def call
      user
    end
  
    private
  
    attr_reader :headers
  
    # renvoie l'utilisateur ou renvoie une erreur
    # Fondamentalement, si User.find () renvoie un ensemble vide ou decoded_auth_token renvoie false, @user sera nul
    def user
      @user ||= AuthApi.find(decoded_auth_token[:user_id]) if decoded_auth_token
      @user || errors.add(:token, 'Invalid token') && nil
    end
  
    # décode le token reçu de http_auth_header et récupère l'ID de l'utilisateur.
    def decoded_auth_token
      @decoded_auth_token ||= JsonWebToken.decode(http_auth_header)
    end
  
    # extrait le token de l'en-tête d'autorisation reçu lors de l'initialisation de la classe
    def http_auth_header
      if headers['Authorization'].present?
        return headers['Authorization'].split(' ').last
      else
        errors.add(:token, 'Missing token')
      end
      nil
    end

  end