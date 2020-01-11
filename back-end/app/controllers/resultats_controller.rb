
require 'ResultatService'

class ResultatsController < ApplicationController

    # Afficher les résultats d'un sondage
    def result
        resultats = ResultatService.instance.resultats(params[:id_sondage])
        render json: resultats, status: :ok
    end

end