# La première méthode 'encode' prend trois paramètres - l'ID utilisateur, le délai d'expiration (1 jour) et la clé de base unique de l'application Rails - pour créer un token unique.
# La deuxième méthode, 'decode' prend le token et utilise la clé secrète de l'application pour le décoder.
# Voici les deux cas dans lesquels ces méthodes seront utilisées:
# Pour authentifier l'utilisateur et générer un token pour lui à l'aide de l'encodage.
# Pour vérifier si le token de l'utilisateur ajouté à chaque demande est correct en utilisant le décodage.

class JsonWebToken
    class << self
      def encode(payload, exp = 24.hours.from_now)
        payload[:exp] = exp.to_i
        JWT.encode(payload, Rails.application.secrets.secret_key_base)
      end
   
      def decode(token)
        body = JWT.decode(token, Rails.application.secrets.secret_key_base)[0]
        HashWithIndifferentAccess.new body
      rescue
        nil
      end
      
    end
end