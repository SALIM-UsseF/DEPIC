class ApplicationController < ActionController::API

    #Home page
    def index
        render json: {status: 'SUCCESS', message: 'HOME PAGE'}, status: :ok
    end
    
end
