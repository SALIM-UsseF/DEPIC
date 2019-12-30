class CreateUtilisateurs < ActiveRecord::Migration[5.2]
  def change
    create_table :utilisateurs, :id => false do |t|
      t.integer :id_utilisateur, primary_key: true
      t.string :email, :null => false
      t.string :adresseIp, :null => false
      t.boolean :etat

      t.timestamps
    end
  end
end
