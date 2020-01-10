package com.android.application.applicationmobiledepic.BaseDeDonneesInterne.DAO

//import android.content.ContentValues
//import android.content.Context
//import android.database.Cursor
//import android.util.Log
//
//import com.android.application.applicationmobiledepic.BaseDeDonneesInterne.Entities.Question
//
//class QuestionDAO(pContext: Context) : DAOBase(pContext) {
//
//    /**
//     * @param p la question à ajouter à la base
//     */
//    fun ajouter(p: Question?) {
//        if (p != null) {
//            val value = ContentValues()
//            value.put(QuestionDAO.NUMERO, p.numero)
//            value.put(QuestionDAO.INTITULE, p.intitule)
//            value.put(QuestionDAO.TYPE_QUESTION, p.type_Question)
//            value.put(QuestionDAO.ID_QUESTION, p.id_Question)
//            value.put(QuestionDAO.ID_SONDAGE, p.id_Sondage)
//            db!!.insert(QuestionDAO.TABLE_NAME, null, value)
//        } else {
//            Log.e("Ajouter QuestionDAO", "La question est nulle.")
//        }
//    }
//
//    /**
//     * @param id l'identifiant de la question à supprimer
//     */
//    fun supprimer(id: Long) {
//        db!!.delete(TABLE_NAME, "$KEY = ?", arrayOf(id.toString()))
//    }
//
//    /**
//     * @param p la question modifié
//     */
//    fun modifier(p: Question) {
//        val value = ContentValues()
//        value.put(QuestionDAO.NUMERO, p.numero)
//        value.put(QuestionDAO.INTITULE, p.intitule)
//        value.put(QuestionDAO.TYPE_QUESTION, p.type_Question)
//        value.put(QuestionDAO.ID_QUESTION, p.id_Question)
//        value.put(QuestionDAO.ID_SONDAGE, p.id_Sondage)
//        db!!.update(TABLE_NAME, value, "$KEY = ?", arrayOf(p.id.toString()))
//    }
//
//    /**
//     * @param id l'identifiant de la question à récupérer
//     */
//    fun selectionner(id: Long): Question? {
//        // CODE
//        return null
//    }
//
//
//    fun affichage() {
//        val cursor: Cursor
//        cursor = db!!.rawQuery("SELECT " + QuestionDAO.KEY + ", " + QuestionDAO.NUMERO + ", " + QuestionDAO.INTITULE + ", " + QuestionDAO.TYPE_QUESTION
//                + ", " + QuestionDAO.ID_QUESTION + ", " + QuestionDAO.ID_SONDAGE + " FROM " + QuestionDAO.TABLE_NAME, null)
//
//        while (cursor.moveToNext()) {
//            val id = cursor.getLong(0)
//            val numero = cursor.getInt(1)
//            val intitule = cursor.getString(2)
//            val type_question = cursor.getString(3)
//            val quest = cursor.getLong(4)
//            val sondage = cursor.getLong(5)
//
//            Log.e("Affichage", "Question de numero $id et de numero Interne $numero étant $intitule de type $type_question et $quest du sondage $sondage")
//        }
//        cursor.close()
//    }
//
//
//    fun CursorToQuestion(cursor: Cursor): Question {
//        val question: Question
//        question = Question(cursor.getLong(0), cursor.getInt(1),
//                cursor.getString(2), cursor.getString(3),
//                cursor.getLong(4), cursor.getLong(5))
//        return question
//    }
//
//    companion object {
//
//        val TABLE_NAME = "question"
//        val KEY = "id"
//        val NUMERO = "numero"
//        val INTITULE = "intitule"
//        val TYPE_QUESTION = "type_Question"
//        val ID_QUESTION = "id_Question"
//        val ID_SONDAGE = "id_Sondage"
//
//
//        val TABLE_CREATE = ("CREATE TABLE "
//                + TABLE_NAME + " ("
//                + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + NUMERO + " INTEGER, "
//                + INTITULE + " TEXT, "
//                + TYPE_QUESTION + " TEXT, "
//                + ID_QUESTION + " INTEGER, "
//                + ID_SONDAGE + " INTEGER,"
//                + " FOREIGN KEY(" + ID_QUESTION + ") REFERENCES " + QuestionDAO.TABLE_NAME + "(" + QuestionDAO.KEY + "),"
//                + " FOREIGN KEY(" + ID_SONDAGE + ") REFERENCES " + ParametreDAO.TABLE_NAME + "(" + ParametreDAO.KEY + "));")
//
//
//        val TABLE_DROP = "DROP TABLE IF EXISTS $TABLE_NAME;"
//    }
//}
