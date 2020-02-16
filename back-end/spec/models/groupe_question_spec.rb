require 'rails_helper'

RSpec.describe GroupeQuestion, type: :model do

    it 'ensures intitule presence' do
        quest = GroupeQuestion.new(estObligatoire: true, ordre: 1, sondage_id: 1).save
        expect(quest).to eq(false)
    end

    it 'ensures estObligatoire presence' do
        quest = GroupeQuestion.new(intitule: 'TestRSpec', ordre: 1, sondage_id: 1).save
        expect(quest).to eq(false)
    end

    it 'ensures ordre presence' do
        quest = GroupeQuestion.new(intitule: 'TestRSpec', estObligatoire: true, sondage_id: 1).save
        expect(quest).to eq(false)
    end

    it 'ensures sondage_id presence' do
        quest = GroupeQuestion.new(intitule: 'TestRSpec', estObligatoire: true, ordre: 1).save
        expect(quest).to eq(false)
    end

    it 'should save successfully' do
        quest = GroupeQuestion.new(intitule: 'TestRSpec', estObligatoire: true, ordre: 1, sondage_id: 1).save
        expect(quest).to eq(true)
    end

    context 'scope tests' do

    let (:params) { {intitule: 'TestRSpec', estObligatoire: true, ordre: 1, sondage_id: 1} }

    before(:each) do

        GroupeQuestion.new(params).save
        GroupeQuestion.new(params).save
        GroupeQuestion.new(params.merge(etat: true)).save
        GroupeQuestion.new(params.merge(etat: false)).save
        GroupeQuestion.new(params.merge(etat: false)).save

    end

    it 'should return active question' do
        expect(Question.active_question.size).to eq(4)
    end

    it 'should return inactive question' do
        expect(Question.inactive_question.size).to eq(1)
    end

    end

end
