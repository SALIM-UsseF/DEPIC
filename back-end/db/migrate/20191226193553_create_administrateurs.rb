class CreateAdministrateurs < ActiveRecord::Migration[5.2]
  def change
    create_table :administrateurs, :id => false do |t|

      t.integer :id_administrateur, primary_key: true
      t.string :pseudo_administrateur, :null => false
      t.string :email_administrateur, :null => false
      t.text :motDePasse_administrateur, :null => false
      t.boolean :supAdmin, :default => false, :null => false
      t.boolean :etat, :default => false, :null => false
      t.timestamps
      
    end
  end
end
