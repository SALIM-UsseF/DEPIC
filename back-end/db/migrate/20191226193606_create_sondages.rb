class CreateSondages < ActiveRecord::Migration[5.2]
  def change
    create_table :sondages, :id => false do |t|

      t.integer :id_sondage, primary_key: true
      t.string :intituleSondage, :null => false
      t.text :descriptionSondage, :null => false
      t.boolean :etat, :default => false, :null => false
      t.references :administrateur, index: true
      t.boolean :publier, :default => false, :null => false
      t.timestamps
    end
    add_foreign_key :sondages, :administrateurs, column: :administrateur_id, primary_key: "id_administrateur"
  end
end
