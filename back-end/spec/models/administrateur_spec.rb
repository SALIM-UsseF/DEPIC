require 'rails_helper'

RSpec.describe Administrateur, type: :model do

  context 'validation tests' do

    it 'ensures pseudo_administrateur presence' do
      admin = Administrateur.new(email_administrateur: 'TestRSpec@gmail.com', motDePasse_administrateur: 'TestRSpec').save
      expect(admin).to eq(false)
    end

    it 'ensures email_administrateur presence' do
      admin = Administrateur.new(pseudo_administrateur: 'TestRSpec', motDePasse_administrateur: 'TestRSpec').save
      expect(admin).to eq(false)
    end

    it 'ensures motDePasse_administrateur presence' do
      admin = Administrateur.new(pseudo_administrateur: 'TestRSpec', email_administrateur: 'TestRSpec@gmail.com').save
      expect(admin).to eq(false)
    end

    it 'should save successfully' do
      admin = Administrateur.new(pseudo_administrateur: 'TestRSpec', email_administrateur: 'TestRSpec@gmail.com', motDePasse_administrateur: 'TestRSpec').save
      expect(admin).to eq(true)
    end

  end

  context 'scope tests' do

    let (:params) { {pseudo_administrateur: 'TEST', email_administrateur: 'test@test.com', motDePasse_administrateur: 'test'} }

    before(:each) do

      Administrateur.new(params).save
      Administrateur.new(params).save
      Administrateur.new(params.merge(etat: true)).save
      Administrateur.new(params.merge(etat: false)).save
      Administrateur.new(params.merge(etat: false)).save

    end

    it 'should return active admin' do
      expect(Administrateur.active_admin.size).to eq(4)
    end

    it 'should return inactive admin' do
      expect(Administrateur.inactive_admin.size).to eq(1)
    end

  end

end
