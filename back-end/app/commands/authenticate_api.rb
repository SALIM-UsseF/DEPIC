# Cette class prend l'e-mail et le mot de passe de l'utilisateur, puis renvoie un 'user', si les informations d'identification correspondent
# La commande prend les paramètres et initialise une instance de classe avec des attributs d'email et de mot de passe qui sont accessibles dans la classe
# La méthode "user" utilise les informations d'identification pour vérifier si l'utilisateur existe dans la base de données en utilisant AuthApi.find_by_email
# Si l'utilisateur existe, on utilise la méthode authenticate intégrée

class AuthenticateApi

    # SimpleCommand: un moyen facile de créer des services
    # Son rôle est similaire à celui d'un helper, mais il facilite plutôt la connexion entre le contrôleur et le modèle, plutôt que le contrôleur et la vue.
    # De cette façon, nous pouvons raccourcir le code dans les modèles et les contrôleurs.
    prepend SimpleCommand
  
    # les paramètres sont pris lors de l'appel de la commande
    def initialize(email, password)
      @email = email
      @password = password
    end
  
    # le résultat retourné
    def call
      JsonWebToken.encode(user_id: user.id) if user
    end
  
    private
  
    attr_accessor :email, :password
  
    def user
      user = AuthApi.find_by_email(email)
      return user if user && user.authenticate(password)
  
      errors.add :user_authentication, 'invalid credentials'
      nil
    end

end