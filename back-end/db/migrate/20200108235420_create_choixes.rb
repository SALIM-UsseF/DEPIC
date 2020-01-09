class CreateChoixes < ActiveRecord::Migration[5.2]
  def change
    create_table(:choixes, :id => false) do |t|
      t.integer :id_choix, primary_key: true
      t.text :intituleChoix, :null => false
      t.boolean :etat, :default => false

      t.references :question, index: true
      t.timestamps
    end
    add_foreign_key :choixes, :questions, column: :question_id, primary_key: "id_question"
  end
end
