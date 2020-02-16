require 'rails_helper'

RSpec.describe Choix, type: :model do


  it 'ensures intitule presence' do
    cat = Choix.new(question_id: 1).save
    expect(cat).to eq(false)
  end

  it 'ensures question_id presence' do
    cat = Choix.new(intituleChoix: 'TestRSpec').save
    expect(cat).to eq(false)
  end

  it 'should save successfully' do
    cat = Choix.new(intituleChoix: 'TestRSpec', question_id: 1).save
    expect(cat).to eq(true)
  end

  context 'scope tests' do

    let (:params) { {intituleChoix: 'TEST', question_id: 1} }

    before(:each) do

      Choix.new(params).save
      Choix.new(params).save
      Choix.new(params.merge(etat: true)).save
      Choix.new(params.merge(etat: false)).save
      Choix.new(params.merge(etat: false)).save

    end

    it 'should return active choix' do
      expect(Choix.active_choix.size).to eq(4)
    end

    it 'should return inactive choix' do
      expect(Choix.inactive_choix.size).to eq(1)
    end

  end

end
