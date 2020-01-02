class CreateParticipers < ActiveRecord::Migration[5.2]
  def change
    create_table(:participers, primary_key: [:id_utilisateur, :id_sondage, :id_question]) do |t|
      t.integer :id_utilisateur
      t.integer :id_sondage
      t.integer :id_question
      t.text :reponse, :null => false
      t.timestamps
      t.boolean :etat
      
    end
  end
end
