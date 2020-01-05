class CreateQuestionPoints < ActiveRecord::Migration[5.2]
  def change
    create_table :question_points do |t|

      t.timestamps
    end
  end
end
