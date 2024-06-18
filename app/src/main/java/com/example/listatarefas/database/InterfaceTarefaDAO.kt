package com.example.listatarefas.database

import com.example.listatarefas.model.Tarefa

interface InterfaceTarefaDAO {
    fun salvar (tarefa: Tarefa): Boolean
    fun atualizar (tarefa: Tarefa): Boolean
    fun excluir (idTarefa: Int): Boolean
    fun listar (): List<Tarefa>
}