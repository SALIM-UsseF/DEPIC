class CreateGroupeQuestions < ActiveRecord::Migration[5.2]
  def change
    create_table :groupe_questions do |t|

      t.timestamps
    end
  end
end
