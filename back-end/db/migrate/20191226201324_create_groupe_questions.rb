class CreateGroupeQuestions < ActiveRecord::Migration[5.2]
  def change
    create_table(:groupe_questions, primary_key: [:id_question]) do |t|
      t.integer :id_question

      t.text :intitule, :null => false
      t.boolean :estObligatoire
      t.integer :ordre, :default => 0, :null => false
      t.boolean :etat, :default => false, :null => false
      t.references :sondage, index: true
      t.string :numerosDeQuestions, :null => false
      t.timestamps
    end
    add_foreign_key :groupe_questions, :sondages, column: :sondage_id, primary_key: "id_sondage"
  end
end
