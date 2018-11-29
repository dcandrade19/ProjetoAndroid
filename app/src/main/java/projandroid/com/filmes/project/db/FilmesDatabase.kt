package projandroid.com.filmes.project.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.launch

@Database(entities = [Filme::class], version = 7)
abstract class FilmesDatabase: RoomDatabase() {

    abstract fun filmeDAO():FilmeDao

    companion object {

        @Volatile
        private var INSTANCE: FilmesDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope):FilmesDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        FilmesDatabase::class.java,
                        "filmes-database"
                )
                        .fallbackToDestructiveMigration()
                        .addCallback(FilmesDatabaseCallBack(scope))
                        .build()
                    INSTANCE = instance
                instance
            }
        }
    }

    private class FilmesDatabaseCallBack(
            private val scope: CoroutineScope
    ): RoomDatabase.Callback(){

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                   //populaTabela(database.filmeDAO())
                }
            }
        }
        /*fun populaTabela(filmeDao: FilmeDao){
            filmeDao.deleteAll()
            filmeDao.insert(Filme(id= 1, nome = "Teste 1", descricao = "desc1"))
            filmeDao.insert(Filme(id= 2, nome = "Teste 2", descricao = "desc2"))
            filmeDao.insert(Filme(id= 3, nome = "Teste 3", descricao = "desc3"))
        }*/
    }

}