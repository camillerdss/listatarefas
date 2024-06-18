package com.example.listatarefas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listatarefas.databinding.ActivityItemTarefaBinding
import com.example.listatarefas.model.Tarefa


class TarefaAdapter(
    //Ação de excluir
    val onClickExcluir: (Int) -> Unit,

    //Ação de Atualizar
    val onClickEditar: (Tarefa) -> Unit

) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    private var listarTarefas: List<Tarefa> = emptyList()

    fun adicionarLista( lista: List<Tarefa> ){
        this.listarTarefas = lista
        notifyDataSetChanged()
    }



    inner class TarefaViewHolder(itemBinding: ActivityItemTarefaBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {

        private val binding: ActivityItemTarefaBinding

        init {
            binding = itemBinding
        }

        fun bind( tarefa: Tarefa ){
            binding.textDescricao.text = tarefa.descricao
            binding.textData.text = tarefa.data

            binding.btnExcluir.setOnClickListener {
                onClickExcluir(tarefa.idTarefa)
            }

            binding.btnEditar.setOnClickListener {
                onClickEditar( tarefa )
            }
        }

    }

    //Cria a Visualização
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemTarefaBinding = ActivityItemTarefaBinding.inflate(
            layoutInflater, parent, false
        )
        return TarefaViewHolder(itemTarefaBinding)
    }

    //Faz o vínculo dos dados para visualização
    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = listarTarefas[position]
        holder.bind( tarefa )
    }


    //Faz a contagem de itens a serem criados na tela
    override fun getItemCount(): Int {
        return listarTarefas.size
    }

}