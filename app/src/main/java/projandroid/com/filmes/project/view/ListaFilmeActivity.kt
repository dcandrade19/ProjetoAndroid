package projandroid.com.filmes.project.view

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lista_filme.*
import kotlinx.android.synthetic.main.item_lista_filme.*
import projandroid.com.filmes.R
import projandroid.com.filmes.project.adapter.FilmeRecyclerAdapter
import projandroid.com.filmes.project.db.Filme
import projandroid.com.filmes.project.viewmodel.FilmeViewModel
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AlertDialog


class ListaFilmeActivity : AppCompatActivity() {

    private lateinit var filmeViewModel: FilmeViewModel
    private val requestCodeAddFilme = 1
    private var filmeSelecionado: Filme? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_filme)

        val recyclerView = rvListaFilme
        val adapter = FilmeRecyclerAdapter(this)
        recyclerView.adapter = adapter

        adapter.onItemClick = {
            Toast.makeText(this, "${it.nome} selecionado!", Toast.LENGTH_LONG).show()
            filmeSelecionado = it
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        filmeViewModel =
                ViewModelProviders.of(this).
                        get(FilmeViewModel::class.java)

        filmeViewModel.listaFilme.observe(this, Observer {filmes->
            filmes?.let { adapter.setListaFilmes(it) }
        })

        fbAdicionarFilme.setOnClickListener{
            filmeSelecionado = null
            val intent = Intent(this@ListaFilmeActivity, NovoFilmeActivity::class.java)
            startActivityForResult(intent, requestCodeAddFilme)
        }

        fbEditarFilme.setOnClickListener {
            if(filmeSelecionado != null) {
                val intent = Intent(this@ListaFilmeActivity, NovoFilmeActivity::class.java)
                intent.putExtra(NovoFilmeActivity.EXTRA_REPLY, filmeSelecionado)
                startActivityForResult(intent, requestCodeAddFilme)
            } else {
                Toast.makeText(applicationContext, "Selecione um filme!", Toast.LENGTH_LONG).show()
            }
        }

        fbDeletarFilme.setOnClickListener {
            if(filmeSelecionado != null) {
                showDialog()
            } else {
                Toast.makeText(applicationContext, "Selecione um filme!", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == requestCodeAddFilme && resultCode == Activity.RESULT_OK){
            data.let {
                val filme : Filme = data?.getSerializableExtra(NovoFilmeActivity.EXTRA_REPLY) as Filme
                if(filme.id > 0) {
                    filmeViewModel.update(filme)
                } else {
                    filmeViewModel.insert(filme)
                }
            }
        }else{
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }

    fun showDialog() {
        // Initialize a new instance of
        val builder = AlertDialog.Builder(this@ListaFilmeActivity)

        // Set the alert dialog title
        builder.setTitle("Confirmar")

        // Display a message on alert dialog
        builder.setMessage("Deseja deletar o filme: ${filmeSelecionado!!.nome}?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("SIM"){dialog, which ->
            filmeViewModel.delete(filmeSelecionado!!)
            Toast.makeText(applicationContext, "Filme deletado!", Toast.LENGTH_LONG).show()
            filmeSelecionado = null
        }

        // Display a negative button on alert dialog
        builder.setNegativeButton("Não"){dialog,which ->

        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }
}

