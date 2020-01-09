require 'singleton'

# Si l'attribut 'etat' égale 'false' donc l'enregistrement est considiré comme non supprimé dans la base de données

class AdministrateurService

    include Singleton
    include UtilsHelper

    # selectionner que les admins non supprimés (etat=false)
    def listeDesAdmins
        administrateurs = Administrateur.where(etat: false).order('created_at ASC')
    end

    # Afficher un admin par ID
    def afficherAdminParId(id_admin)
        administrateur = Administrateur.find_by(id_administrateur: id_admin, etat: false)
    end

    # Creer un nouveau admin
    def creerNouveauAdmin(pseudo_administrateur, email_administrateur, motDePasse_administrateur)

        if motDePasse_administrateur != ''

            motDePasse_administrateur = format_string_to_md5(motDePasse_administrateur) # la partie front-end qui doit faire ce cryptage

            administrateur = Administrateur.new(:pseudo_administrateur => pseudo_administrateur, :email_administrateur => email_administrateur, :motDePasse_administrateur => motDePasse_administrateur)

            if administrateur.save
                newAdmin = administrateur
            else
                newAdmin = nil
            end
        else
            newAdmin = nil
        end 

    end

    # Verifier le login d'un admin
    def loginAdmin(email, motDePasse)

        if motDePasse != '' && email != ''
            administrateur = Administrateur.find_by(email_administrateur: email, motDePasse_administrateur: motDePasse, etat: false)
        else
            administrateur = nil
        end 

    end

    # Verifier le mot de passe d'un admin
    def verifierMotDePasseAdmin(id_admin, psw_admin)
        if psw_admin != '' && id_admin != nil
            administrateur = Administrateur.find_by(id_administrateur: id_admin, motDePasse_administrateur: psw_admin, etat: false)
            correct = (administrateur != nil)
        else
            correct = false
        end
    end

    # Modifier un admin
    def modifierAdmin(id_admin, pseudo, email, psw)
        administrateur = Administrateur.find_by(id_administrateur: id_admin, etat: false)

        if administrateur != nil && administrateur.update_attributes(:pseudo_administrateur => pseudo, :email_administrateur => email, :motDePasse_administrateur => psw)
            modifier = administrateur
        else
            modifier = nil
        end

    end

    # Supprimer un admin par ID
    def supprimerAdmin(id_admin, etat)
        administrateur = Administrateur.find_by(id_administrateur: id_admin, etat: false)

        if administrateur != nil && administrateur.update_attributes(:etat => etat)
            supprimer = true
        else
            supprimer = false
        end

    end

end