class CreateUtilisateurs < ActiveRecord::Migration[5.2]
  def change
    create_table :utilisateurs, :id => false do |t|
      t.integer :id_utilisateur, primary_key: true
      t.string :email, :default => 'unknown', :null => false
      t.string :adresseIp, :default => 'unknown', :null => false
      t.boolean :etat, :default => false, :null => false
      t.timestamps
    end
  end
end
