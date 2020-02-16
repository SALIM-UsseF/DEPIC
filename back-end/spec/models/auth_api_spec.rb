require 'rails_helper'

RSpec.describe AuthApi, type: :model do

  context 'validation tests' do

    it 'ensures email presence' do
      compte = AuthApi.new(password: 'TestRSpec', password_confirmation: 'TestRSpec').save
      expect(compte).to eq(false)
    end

    it 'ensures password presence' do
      compte = AuthApi.new(email: 'TestRSpec@gmail.com').save
      expect(compte).to eq(false)
    end

    it 'ensures password_confirmation presence' do
      compte = AuthApi.new(email: 'TestRSpec@gmail.com', password: 'TestRSpec').save
      expect(compte).to eq(false)
    end

    it 'should not save successfully' do
      compte = AuthApi.new(email: 'TestRSpec@gmail.com', password: 'TestRSpec', password_confirmation: 'TestRSpecERROR').save
      expect(compte).to eq(false)
    end

    it 'should save successfully' do
      compte = AuthApi.new(email: 'TestRSpec@gmail.com', password: 'TestRSpec', password_confirmation: 'TestRSpec').save
      expect(compte).to eq(true)
    end

  end

end
