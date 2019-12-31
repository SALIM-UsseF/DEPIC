class CreateQuestionChoixes < ActiveRecord::Migration[5.2]
  def change
    create_table(:question_choixes, primary_key: [:id_question]) do |t|
      t.integer :id_question
      t.boolean :estUnique
      t.text :lesChoix, :null => false
      t.text :intitule, :null => false
      t.boolean :estObligatoire
      t.integer :ordre, :default => 0, :null => false
      t.boolean :etat, :default => false, :null => false
      t.references :sondage, index: true
      t.timestamps      
    end
    add_foreign_key :question_choixes, :sondages, column: :sondage_id, primary_key: "id_sondage"
  end
end
