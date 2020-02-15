class CreateAuthApis < ActiveRecord::Migration[5.2]
  def change
    create_table :auth_apis do |t|
      t.string :login
      t.string :email
      t.string :password_digest

      t.timestamps
    end
  end
end
