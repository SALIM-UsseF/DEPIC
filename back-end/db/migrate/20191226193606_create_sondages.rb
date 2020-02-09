class CreateSondages < ActiveRecord::Migration[5.2]
  def change
    create_table :sondages, :id => false do |t|

      t.integer :id_sondage, primary_key: true
      t.string :intituleSondage, :null => false
      t.text :descriptionSondage, :null => false
      t.boolean :etat, :default => false
      t.references :administrateur, index: true
      t.references :categorie, index: true
      t.boolean :publier, :default => false
      t.boolean :resultats, :default => false

      t.timestamps

    end
    add_foreign_key :sondages, :administrateurs, column: :administrateur_id, primary_key: "id_administrateur"
    add_foreign_key :sondages, :categories, column: :categorie_id, primary_key: "id_categorie"
  end
end
