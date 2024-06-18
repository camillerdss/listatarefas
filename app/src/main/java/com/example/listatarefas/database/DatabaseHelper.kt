package com.example.listatarefas.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper (context: Context):SQLiteOpenHelper(
    //context, "MinhasTarefas.db",null,1
    context, BD,null,VER
) {
    companion object {
        const val BD = "MinhasTarefas.db"
        const val VER = 1
        const val TABELA = "tarefa"
        const val COL_ID = "id_tarefa"
        const val COL_DESC = "descricao"
        const val COL_DATA = "data"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        Log.i("info_db", "Executou onCreate")

          val sql = "CREATE TABLE IF NOT EXISTS $TABELA (" +
                "$COL_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "$COL_DESC VARCHAR (60)," +
                "$COL_DATA DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ");"


        try {
            //A INTERRAGAÇÃO FAZ UMA CHAMADA SEGURA
            db?.execSQL(sql)
            Log.i("info_db", "Sucesso ao criar a Tabela")
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_db", "Erro ao criar a Tabela")
        }

    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.i("info_db", "Executou onUpgrade")
    }

}