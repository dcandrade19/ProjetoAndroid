package projandroid.com.filmes.project.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import projandroid.com.filmes.project.db.Filme
import projandroid.com.filmes.project.db.FilmesDatabase
import projandroid.com.filmes.project.repository.FilmeRepository
import kotlin.coroutines.experimental.CoroutineContext

class FilmeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FilmeRepository
    val listaFilme: LiveData<List<Filme>>

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
    get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    init {
        val filmeDao = FilmesDatabase.
                getDatabase(application, scope).filmeDAO()

        repository = FilmeRepository(filmeDao)
        listaFilme = repository.listaFilmes
    }

    fun insert(filme: Filme) = scope.launch(Dispatchers.IO){
        repository.insert(filme)
    }

    fun delete(filme: Filme) = scope.launch (Dispatchers.IO) {
        repository.delete(filme)
    }

    fun update(filme: Filme) = scope.launch (Dispatchers.IO) {
        repository.update(filme)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}