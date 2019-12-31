class CreateQuestionPoints < ActiveRecord::Migration[5.2]
  def change
    create_table(:question_points, primary_key: [:id_question]) do |t|
      t.integer :id_question
      t.integer :minPoints, :default => 0, :null => false
      t.integer :maxPoints, :default => 0, :null => false
      t.text :intitule, :null => false
      t.boolean :estObligatoire
      t.integer :ordre, :default => 0, :null => false
      t.boolean :etat, :default => false, :null => false
      t.references :sondage, index: true
      t.timestamps     
    end
    add_foreign_key :question_points, :sondages, column: :sondage_id, primary_key: "id_sondage"
  end
end
