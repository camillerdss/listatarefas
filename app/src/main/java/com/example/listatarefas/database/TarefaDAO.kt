package com.example.listatarefas.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.listatarefas.model.Tarefa

//Projeto Nilton Barros
class TarefaDAO (context: Context) : InterfaceTarefaDAO {

    val escrever = DatabaseHelper (context).writableDatabase
    val ler = DatabaseHelper (context).readableDatabase

    override fun salvar(tarefa: Tarefa): Boolean {
        val conteudos = ContentValues()
        conteudos.put("${DatabaseHelper.COL_DESC}", tarefa.descricao)
        try {
            //insert já implementa o código sql para inserir dados
            escrever.insert(
                DatabaseHelper.TABELA,
                null,
                conteudos
            )
            Log.i("info_db", "Sucesso ao salvar Tarefa")
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_db", "Erro ao salvar Tarefa")
            return false
        }
        return true
    }


    override fun atualizar(tarefa: Tarefa): Boolean {
        val args = arrayOf(tarefa.idTarefa.toString())
        val conteudo = ContentValues()
        conteudo.put("${DatabaseHelper.COL_DESC}", tarefa.descricao)
        try {
            escrever.update(
                DatabaseHelper.TABELA,
                conteudo,
                "${DatabaseHelper.COL_ID} = ?",
                args
            )

            Log.i("info_db", "Sucesso ao Atualizar Tarefa")
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_db", "Erro ao Atualizar Tarefa")
            return false
        }
        return true
    }


    override fun excluir(idTarefa: Int): Boolean {
        val args = arrayOf(idTarefa.toString())
        try {
            escrever.delete(
                DatabaseHelper.TABELA,
                "${DatabaseHelper.COL_ID} = ?",
                args
            )

            Log.i("info_db", "Sucesso ao remover Tarefa")
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_db", "Erro ao remover Tarefa")
            return false
        }
        return true
    }


    override fun listar(): List<Tarefa> {
        val listaTarefas = mutableListOf<Tarefa>()

        //val sql = "SELECT id_tarefa, descricao, data FROM tarefa"

        //Pesquisa no Banco de Dados
        val sql = "SELECT ${DatabaseHelper.COL_ID}, " +
                "${DatabaseHelper.COL_DESC}, " +
                "strftime('%d/%m/%Y %H:%M:%S', ${DatabaseHelper.COL_DATA}) ${DatabaseHelper.COL_DATA} " +
                "FROM ${DatabaseHelper.TABELA}"

        //Cursor para recuperar os dados do Banco de Dados
        val cursor = ler.rawQuery(sql, null)

        //recupera o índice de cada coluna
        val indiceId = cursor.getColumnIndex(DatabaseHelper.COL_ID)
        val indiceDescricao = cursor.getColumnIndex(DatabaseHelper.COL_DESC)
        val indiceData = cursor.getColumnIndex(DatabaseHelper.COL_DATA)

        //Laço de repetição para percorrer toda tabela
        while (cursor.moveToNext()){
            val idTarefa = cursor.getInt( indiceId)
            val descricao = cursor.getString( indiceDescricao)
            val data = cursor.getString( indiceData)

            //Adiciona cada linha da tabela em uma lista
            listaTarefas.add(
                Tarefa(idTarefa, descricao, data)
            )
        }
        //Retorna a lista de tarefas preenchida
        return listaTarefas
    }

}