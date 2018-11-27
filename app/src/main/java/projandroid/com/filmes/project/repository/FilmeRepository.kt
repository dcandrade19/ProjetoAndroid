package projandroid.com.filmes.project.repository

import android.arch.lifecycle.LiveData
import projandroid.com.filmes.project.db.Filme
import projandroid.com.filmes.project.db.FilmeDao

class FilmeRepository(private val filmeDAO: FilmeDao) {

    val listaFilmes: LiveData<List<Filme>> = filmeDAO.getAll()

    fun insert(filme: Filme) {
        filmeDAO.insert(filme)
    }

    fun delete(filme: Filme) {
        filmeDAO.delete(filme)
    }

    fun update(filme: Filme) {
        filmeDAO.update(filme)
    }
}