package com.example.listatarefas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listatarefas.database.TarefaDAO
import com.example.listatarefas.databinding.ActivityMainBinding
import com.example.listatarefas.model.Tarefa

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //Gera uma lista vazia
    private var listarTarefas = emptyList<Tarefa>()
    private var tarefaAdapter: TarefaAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fabAdicionar.setOnClickListener {
            val intent = Intent(this, adicionar_tarefa::class.java)
            startActivity( intent )
        }


        //RecicleView
        tarefaAdapter = TarefaAdapter(
            //Ação de excluir
            {id -> confirmarExclusao(id)},
            //Ação de editar
            {tarefa -> editar(tarefa)}
        )

        binding.rvTarefas.adapter = tarefaAdapter
        binding.rvTarefas.layoutManager = LinearLayoutManager(this)

        //Criando um divisor entre os itens
        binding.rvTarefas.addItemDecoration(
            DividerItemDecoration(this, RecyclerView.VERTICAL )
        )
    }


    //Função para atualização de uma tarefa
    private fun editar(tarefa: Tarefa) {
        //Navegar para tela de adicionar tarefa
        val intent = Intent(this, adicionar_tarefa::class.java)
        intent.putExtra("tarefa", tarefa)
        startActivity( intent )
    }

    //Função para confirmar a exclusão de uma tarefa
    private fun confirmarExclusao(id: Int) {
        val alertBuilder = AlertDialog.Builder(this)

        alertBuilder.setTitle("Confirmar Exclusão")
        alertBuilder.setMessage("Deseja excluir a Tarefa?")



        // O uso do _ se deve pelo fato de não ser necessário os parâmetros
        alertBuilder.setPositiveButton("Sim"){ _, _ ->
            val tarefaDAO = TarefaDAO (this)
            if (tarefaDAO.excluir ( id )){
                atualizarListaTarefas()
                Toast.makeText(this,
                    "Sucesso ao remover tarefa",
                    Toast.LENGTH_SHORT)
                    .show()
            }else{
                Toast.makeText(this,
                    "Erro ao remover tarefa",
                    Toast.LENGTH_SHORT)
                    .show()
            }
            
        }

        alertBuilder.setNegativeButton("Não"){ _, _ -> }

        alertBuilder.create().show()
    }

    private fun atualizarListaTarefas(){

        val tarefaDAO = TarefaDAO(this)
        listarTarefas = tarefaDAO.listar()
        tarefaAdapter?.adicionarLista( listarTarefas )

    }


    override fun onStart() {
        super.onStart()
        atualizarListaTarefas()
    }
}