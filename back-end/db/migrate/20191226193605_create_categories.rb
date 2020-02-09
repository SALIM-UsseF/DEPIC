class CreateCategories < ActiveRecord::Migration[5.2]
  def change
    create_table(:categories, :id => false) do |t|
      t.integer :id_categorie, primary_key: true
      t.text :intitule, :null => false
      t.boolean :etat, :default => false

      t.timestamps
    end
  end
end
