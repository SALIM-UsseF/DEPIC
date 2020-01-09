
require 'ResultatService'
class ResultatsController < ApplicationController

    # Afficher les résultats d'un sondage

    def index

    resultats= ResultatService.instance.Resultats(params[:id])
    render json: {status: 'Success', message: 'Loaded résultat', data: resultats}, status: :ok
    end

end