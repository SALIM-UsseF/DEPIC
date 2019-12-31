class CreateQuestions < ActiveRecord::Migration[5.2]
  def change
    create_table :questions, :id => false do |t|
      t.integer :id_question, primary_key: true
      t.text :intitule, :null => false
      t.boolean :estObligatoire
      t.integer :ordre, :default => 0, :null => false
      t.boolean :etat, :default => false, :null => false
      t.references :sondage, index: true
      t.timestamps
    end
    add_foreign_key :questions, :sondages, column: :sondage_id, primary_key: "id_sondage"
  end
end
