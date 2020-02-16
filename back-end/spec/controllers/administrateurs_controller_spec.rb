require 'rails_helper'

RSpec.describe AdministrateursController, type: :controller do
    context 'GET #index' do
        it 'returns a success response' do
          get :index
          expect(response).to be_successful # response.success?
        end
    end

    context 'GET #show' do
        it 'returns a success response' do
            admin = Administrateur.create!(pseudo_administrateur: 'TEST', email_administrateur: 'test@test.com', motDePasse_administrateur: 'test')
            get :show, params: { id: admin.to_param }
            expect(response).to be_successful
        end
    end
end
