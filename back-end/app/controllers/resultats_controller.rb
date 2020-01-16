
require 'ResultatService'

class ResultatsController < ApplicationController

    # Afficher les résultats d'un sondage
    def result
        resultats = ResultatService.instance.resultats(params[:idSondage])
        render json: resultats, status: :ok
    end

    # renvoie les resultats d'un sondage publié
    def resultatsPublie
        render json: ResultatService.instance.resultatsPublie(params[:idSondage]), status: :ok
    end

end