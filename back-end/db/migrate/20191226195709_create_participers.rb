class CreateParticipers < ActiveRecord::Migration[5.2]
  def change
    create_table(:participers, primary_key: [:id_utilisateur, :id_sondage, :id_question]) do |t|

      t.integer :id_utilisateur, :null => false
      t.integer :id_sondage, :null => false
      t.integer :id_question, :null => false
      t.text :reponse
      t.boolean :etat, :default => false

      t.timestamps      
    end
  end
end
