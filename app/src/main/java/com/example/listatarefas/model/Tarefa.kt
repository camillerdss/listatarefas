package com.example.listatarefas.model

import java.io.Serializable

data class Tarefa (
    val idTarefa: Int,
    val descricao: String,
    val data: String
) : Serializable