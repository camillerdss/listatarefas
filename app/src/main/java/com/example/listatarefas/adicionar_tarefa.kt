package com.example.listatarefas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listatarefas.database.TarefaDAO
import com.example.listatarefas.databinding.ActivityAdicionarTarefaBinding
import com.example.listatarefas.model.Tarefa

class adicionar_tarefa : AppCompatActivity() {
    private val binding by lazy {
        ActivityAdicionarTarefaBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Recuperar uma tarefa para edição
        var tarefa: Tarefa? = null
        val bundle = intent.extras
        if (bundle != null){
            tarefa = bundle.getSerializable("tarefa") as Tarefa
            binding.editTarefa.setText(tarefa.descricao)
        }


        binding.btnSalvar.setOnClickListener {
            if (binding.editTarefa.text.isNotEmpty()) {

                if (tarefa != null){
                    editar( tarefa )
                }else{
                    salvar()
                }
            } else {
                Toast.makeText(
                    this,
                    "Preencha uma tarefa",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    //Função Editar
    private fun editar(tarefa: Tarefa) {
        val descricaoTarefa = binding.editTarefa.text.toString()
        val tarefaAtualizada = Tarefa(
            tarefa.idTarefa, descricaoTarefa, "default"
        )
        val tarefaDAO = TarefaDAO(this)
        //tarefaDAO.atualizar(tarefaAtualizada)
        if (tarefaDAO.atualizar(tarefaAtualizada)){
            Toast.makeText(
                this,
                "Preencha uma tarefa",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    //Função Salvar
    private fun salvar(){
        val descricaoTarefa = binding.editTarefa.text.toString()
        val tarefa = Tarefa(
            idTarefa = -1, descricaoTarefa, "default"
        )

        val tarefaDAO = TarefaDAO(this)
        if (tarefaDAO.salvar(tarefa)) {
            Toast.makeText(
                this,
                "Preencha uma tarefa",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
}