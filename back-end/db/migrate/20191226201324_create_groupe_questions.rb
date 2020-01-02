class CreateGroupeQuestions < ActiveRecord::Migration[5.2]
  def change
    create_table(:groupe_questions, primary_key: [:id_question]) do |t|
      t.integer :id_question

      t.text :intitule, :null => false
      t.boolean :estObligatoire
      t.integer :ordre, :default => 0, :null => false
      t.timestamps
      t.boolean :etat
      t.references :sondage, index: true
      t.string :numerosDeQuestions, :null => false



      
    end
    add_foreign_key :groupe_questions, :sondages, column: :sondage_id, primary_key: "id_sondage"
  end
end
