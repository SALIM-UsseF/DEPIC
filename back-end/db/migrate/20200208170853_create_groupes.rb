class CreateGroupes < ActiveRecord::Migration[5.2]
  def change
    create_table(:groupes, primary_key: [:id_groupe, :id_question]) do |t|
      t.integer :id_groupe
      t.integer :id_question

      t.boolean :etat, :default => false

      t.timestamps
    end
  end
end
