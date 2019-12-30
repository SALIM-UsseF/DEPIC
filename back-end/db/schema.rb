# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 2019_12_26_201324) do

  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"

  create_table "administrateurs", primary_key: "id_administrateur", id: :serial, force: :cascade do |t|
    t.string "pseudo_administrateur", null: false
    t.string "email_administrateur", null: false
    t.text "motDePasse_administrateur", null: false
    t.integer "supAdmin", default: 0, null: false
    t.boolean "etat"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "groupe_questions", primary_key: "id_question", id: :integer, default: nil, force: :cascade do |t|
    t.text "intitule", null: false
    t.boolean "estObligatoire"
    t.integer "ordre", default: 0, null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.boolean "etat"
    t.bigint "sondage_id"
    t.string "numerosDeQuestions", null: false
    t.index ["sondage_id"], name: "index_groupe_questions_on_sondage_id"
  end

  create_table "participers", primary_key: ["id_utilisateur", "id_sondage", "id_question"], force: :cascade do |t|
    t.integer "id_utilisateur", null: false
    t.integer "id_sondage", null: false
    t.integer "id_question", null: false
    t.text "reponse", null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.boolean "etat"
  end

  create_table "question_choixes", primary_key: "id_question", id: :integer, default: nil, force: :cascade do |t|
    t.boolean "estUnique"
    t.text "lesChoix", null: false
    t.text "intitule", null: false
    t.boolean "estObligatoire"
    t.integer "ordre", default: 0, null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.boolean "etat"
    t.bigint "sondage_id"
    t.index ["sondage_id"], name: "index_question_choixes_on_sondage_id"
  end

  create_table "question_ouvertes", primary_key: "id_question", id: :integer, default: nil, force: :cascade do |t|
    t.text "nombreDeCaractere", null: false
    t.text "intitule", null: false
    t.boolean "estObligatoire"
    t.integer "ordre", default: 0, null: false
    t.boolean "etat"
    t.bigint "sondage_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["sondage_id"], name: "index_question_ouvertes_on_sondage_id"
  end

  create_table "question_points", primary_key: "id_question", id: :integer, default: nil, force: :cascade do |t|
    t.integer "minPoints", default: 0, null: false
    t.integer "maxPoints", default: 0, null: false
    t.text "intitule", null: false
    t.boolean "estObligatoire"
    t.integer "ordre", default: 0, null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.boolean "etat"
    t.bigint "sondage_id"
    t.index ["sondage_id"], name: "index_question_points_on_sondage_id"
  end

  create_table "questions", primary_key: "id_question", id: :serial, force: :cascade do |t|
    t.text "intitule", null: false
    t.boolean "estObligatoire"
    t.integer "ordre", default: 0, null: false
    t.boolean "etat"
    t.bigint "sondage_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["sondage_id"], name: "index_questions_on_sondage_id"
  end

  create_table "sondages", primary_key: "id_sondage", id: :serial, force: :cascade do |t|
    t.string "intituleSondage", null: false
    t.text "descriptionSondage", null: false
    t.boolean "etat"
    t.bigint "administrateur_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["administrateur_id"], name: "index_sondages_on_administrateur_id"
  end

  create_table "utilisateurs", primary_key: "id_utilisateur", id: :serial, force: :cascade do |t|
    t.string "email", null: false
    t.string "adresseIp", null: false
    t.boolean "etat"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  add_foreign_key "groupe_questions", "sondages", primary_key: "id_sondage"
  add_foreign_key "question_choixes", "sondages", primary_key: "id_sondage"
  add_foreign_key "question_ouvertes", "sondages", primary_key: "id_sondage"
  add_foreign_key "question_points", "sondages", primary_key: "id_sondage"
  add_foreign_key "questions", "sondages", primary_key: "id_sondage"
  add_foreign_key "sondages", "administrateurs", primary_key: "id_administrateur"
end
