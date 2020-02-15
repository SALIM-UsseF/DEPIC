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

ActiveRecord::Schema.define(version: 2020_02_14_194142) do

  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"

  create_table "administrateurs", primary_key: "id_administrateur", id: :serial, force: :cascade do |t|
    t.string "pseudo_administrateur", null: false
    t.string "email_administrateur", null: false
    t.text "motDePasse_administrateur", null: false
    t.boolean "supAdmin", default: false, null: false
    t.boolean "etat", default: false, null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "auth_apis", force: :cascade do |t|
    t.string "login"
    t.string "email"
    t.string "password_digest"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "categories", primary_key: "id_categorie", id: :serial, force: :cascade do |t|
    t.text "intitule", null: false
    t.boolean "etat", default: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "choixes", primary_key: "id_choix", id: :serial, force: :cascade do |t|
    t.text "intituleChoix", null: false
    t.boolean "etat", default: false
    t.bigint "question_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["question_id"], name: "index_choixes_on_question_id"
  end

  create_table "groupe_questions", force: :cascade do |t|
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "groupes", primary_key: ["id_groupe", "id_question"], force: :cascade do |t|
    t.integer "id_groupe", null: false
    t.integer "id_question", null: false
    t.boolean "etat", default: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "participers", primary_key: ["id_utilisateur", "id_sondage", "id_question"], force: :cascade do |t|
    t.integer "id_utilisateur", null: false
    t.integer "id_sondage", null: false
    t.integer "id_question", null: false
    t.text "reponse"
    t.boolean "etat", default: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "question_choixes", force: :cascade do |t|
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "question_ouvertes", force: :cascade do |t|
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "question_points", force: :cascade do |t|
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "questions", primary_key: "id_question", id: :serial, force: :cascade do |t|
    t.text "intitule", null: false
    t.boolean "estObligatoire", null: false
    t.string "type", null: false
    t.integer "nombreDeCaractere"
    t.float "minPoints"
    t.float "maxPoints"
    t.boolean "estUnique"
    t.integer "ordre", default: 0
    t.boolean "etat", default: false
    t.bigint "sondage_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["sondage_id"], name: "index_questions_on_sondage_id"
  end

  create_table "sondages", primary_key: "id_sondage", id: :serial, force: :cascade do |t|
    t.string "intituleSondage", null: false
    t.text "descriptionSondage", null: false
    t.boolean "etat", default: false
    t.bigint "administrateur_id"
    t.bigint "categorie_id"
    t.boolean "publier", default: false
    t.boolean "resultats", default: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["administrateur_id"], name: "index_sondages_on_administrateur_id"
    t.index ["categorie_id"], name: "index_sondages_on_categorie_id"
  end

  create_table "utilisateurs", primary_key: "id_utilisateur", id: :serial, force: :cascade do |t|
    t.string "email"
    t.string "adresseIp"
    t.boolean "etat", default: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  add_foreign_key "choixes", "questions", primary_key: "id_question"
  add_foreign_key "questions", "sondages", primary_key: "id_sondage"
  add_foreign_key "sondages", "administrateurs", primary_key: "id_administrateur"
  add_foreign_key "sondages", "categories", column: "categorie_id", primary_key: "id_categorie"
end
