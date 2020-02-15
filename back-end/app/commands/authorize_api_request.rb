class AuthorizeApiRequest

    prepend SimpleCommand
  
    def initialize(headers = {})
      @headers = headers
    end
  
    def call
      user
    end
  
    private
  
    attr_reader :headers
  
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