package projandroid.com.filmes.project.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
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
        if(current.imagemUri.isEmpty()) {
            holder.imagemFilme.setImageResource(R.drawable.default_poster)
            //holder.imagemFilme.visibility = View.VISIBLE
        } else {
            holder.imagemFilme.setImageURI(Uri.parse(current.imagemUri))
        }
        if(current.favorito) {
            holder.favoritoFilme.setImageResource(R.drawable.ic_favorite_true_24dp)
        } else {
            holder.favoritoFilme.setImageResource(R.drawable.ic_favorite_false_24dp)
        }
        holder.classificacaoFilme.rating = current.classificacao
        holder.duracaoFilme.text = current.duracao.toString()
        holder.anoLancamentoFilme.text = current.anoLancamento
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nomeFilme: TextView = itemView.txtFilmeListaNome
        val descricaoFilme: TextView = itemView.txtFilmeListaDescricao
        val imagemFilme: ImageView = itemView.imgFilmeListaFoto
        val classificacaoFilme: RatingBar = itemView.rbFilmesListaClassificacao
        val favoritoFilme: ImageView = itemView.imgFilmeListaFavorito
        val duracaoFilme: TextView = itemView.txtFilmeListaDuracao
        val anoLancamentoFilme: TextView = itemView.txtFilmeListaAnoLancamento

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