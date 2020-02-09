require 'singleton'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class CategorieService

    include Singleton

    # selectionner que les categories non supprimés (etat=false)
    def listeDesCategories
        categories = Categorie.where(etat: false).order('created_at ASC')
    end

    # Afficher une categorie par ID
    def afficherCategorieParId(id_categorie)
        categorie = Categorie.find_by(id_categorie: id_categorie, etat: false)
    end

    # Creer une categorie
    def creerCategorie(intitule)

            categorie = Categorie.new(:intitule => intitule)

            if categorie.save
                newCategorie = categorie
            else
                newCategorie = nil
            end

    end

    # Modifier une categorie
    def modifierCategorie(id_categorie, intitule)
        categorie = Categorie.find_by(id_categorie: id_categorie, etat: false);

        if categorie != nil && Categorie.update_attributes(:intitule => intitule)
            modifier = categorie
        else
            modifier = nil
        end

    end

    # Supprimer une categorie par ID
    def supprimerCategorie(id_categorie, etat)
        categorie = Categorie.find_by(id_categorie: id_categorie, etat: false)
        supprimer = (categorie != nil && Categorie.update_attributes(:etat => etat)) 
    end

end