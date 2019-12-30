class CreateQuestionOuvertes < ActiveRecord::Migration[5.2]
  def change
    create_table(:question_ouvertes, primary_key: [:id_question]) do |t|
      t.integer :id_question
      t.text :nombreDeCaractere, :null => false
      t.text :intitule, :null => false
      t.boolean :estObligatoire
      t.integer :ordre, :default => 0, :null => false
      
      t.boolean :etat
      t.references :sondage, index: true
      t.timestamps
      
    end
    add_foreign_key :question_ouvertes, :sondages, column: :sondage_id, primary_key: "id_sondage"
  end
end
