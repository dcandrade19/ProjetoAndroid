package projandroid.com.filmes.project.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_novo_filme.view.*
import kotlinx.android.synthetic.main.item_lista_filme.view.*
import projandroid.com.filmes.R
import projandroid.com.filmes.project.db.Filme
import projandroid.com.filmes.project.view.ListaFilmeActivity

class FilmeRecyclerAdapter internal constructor(context: Context) :
RecyclerView.Adapter<FilmeRecyclerAdapter.ViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var filmes = emptyList<Filme>()
    var onItemClick: ((Filme) -> Unit)? = null

    override fun onCreateViewHolder(holder: ViewGroup, position: Int): FilmeRecyclerAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.item_lista_filme, holder, false)
        return ViewHolder(view)
    }


    override fun getItemCount() = filmes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = filmes[position]
        holder.nomeFilme.text = current.nome
        holder.descricaoFilme.text = current.descricao
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nomeFilme: TextView = itemView.txtFilmeListaNome
        val descricaoFilme: TextView = itemView.txtFilmeListaDescricao

        init{
            itemView.setOnClickListener{
                onItemClick?.invoke(filmes[adapterPosition])
            }
        }
    }

    fun setListaFilmes(listaFilmes: List<Filme>) {
        this.filmes = listaFilmes
        notifyDataSetChanged()
    }
}