class CreateQuestionOuvertes < ActiveRecord::Migration[5.2]
  def change
    create_table :question_ouvertes do |t|

      t.timestamps
    end
  end
end
