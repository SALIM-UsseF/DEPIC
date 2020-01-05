class CreateUtilisateurs < ActiveRecord::Migration[5.2]
  def change
    create_table :utilisateurs, :id => false do |t|
      t.integer :id_utilisateur, primary_key: true
      t.string :email
      t.string :adresseIp
      t.boolean :etat, :default => false

      t.timestamps
    end
  end
end
