class ApplicationController < ActionController::API

    #Home page
    def index
        render json: {status: 'SUCCESS', message: 'DPIC - API'}, status: :ok
    end
    
end
