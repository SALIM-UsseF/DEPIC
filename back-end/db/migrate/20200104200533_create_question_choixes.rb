class CreateQuestionChoixes < ActiveRecord::Migration[5.2]
  def change
    create_table :question_choixes do |t|

      t.timestamps
    end
  end
end
