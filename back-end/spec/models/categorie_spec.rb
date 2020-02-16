require 'rails_helper'

RSpec.describe Categorie, type: :model do

  context 'validation tests' do

    it 'ensures intitule presence' do
      cat = Categorie.new().save
      expect(cat).to eq(false)
    end

    it 'should save successfully' do
      cat = Categorie.new(intitule: 'TestRSpec').save
      expect(cat).to eq(true)
    end

  end

  context 'scope tests' do

    let (:params) { {intitule: 'TEST'} }

    before(:each) do

      Categorie.new(params).save
      Categorie.new(params).save
      Categorie.new(params.merge(etat: true)).save
      Categorie.new(params.merge(etat: false)).save
      Categorie.new(params.merge(etat: false)).save

    end

    it 'should return active cat' do
      expect(Categorie.active_cat.size).to eq(4)
    end

    it 'should return inactive cat' do
      expect(Categorie.inactive_cat.size).to eq(1)
    end

  end

end
