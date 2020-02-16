require 'rails_helper'

RSpec.describe Sondage, type: :model do

  it 'ensures intitule presence' do
    sond = Sondage.new(descriptionSondage: 'TEST', administrateur_id: 1).save
    expect(sond).to eq(false)
  end

  it 'ensures description presence' do
    sond = Sondage.new(intituleSondage: 'TestRSpec', administrateur_id: 1).save
    expect(sond).to eq(false)
  end

  it 'ensures administrateur_id presence' do
    sond = Sondage.new(intituleSondage: 'TestRSpec', descriptionSondage: 'TEST').save
    expect(sond).to eq(false)
  end

  it 'should save successfully' do
    sond = Sondage.new(intituleSondage: 'TestRSpec', descriptionSondage: 'TEST', administrateur_id: 1).save
    expect(sond).to eq(true)
  end

  context 'scope tests' do

    let (:params) { {intituleSondage: 'TestRSpec', descriptionSondage: 'TEST', administrateur_id: 1} }

    before(:each) do

      Sondage.new(params).save
      Sondage.new(params).save
      Sondage.new(params.merge(etat: true)).save
      Sondage.new(params.merge(etat: false)).save
      Sondage.new(params.merge(etat: false)).save

    end

    it 'should return active Sondage' do
      expect(Sondage.active_sondage.size).to eq(4)
    end

    it 'should return inactive Sondage' do
      expect(Sondage.inactive_sondage.size).to eq(1)
    end

  end

end
