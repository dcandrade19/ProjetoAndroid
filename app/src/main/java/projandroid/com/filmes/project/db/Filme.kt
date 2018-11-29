package projandroid.com.filmes.project.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable
import java.sql.Time
import java.util.*

@Entity(tableName = "filmes")
data class Filme (

        @ColumnInfo(name = "nome")
        var nome: String,
        @ColumnInfo(name = "descricao")
        var descricao: String,
        @ColumnInfo(name = "imagemUri")
        var imagemUri: String = "",
        @ColumnInfo(name = "favorito")
        var favorito: Boolean = false,
        @ColumnInfo(name = "anoLancamento")
        var anoLancamento: String,
        @ColumnInfo(name = "classificacao")
        var classificacao: Float = 0f,
        @ColumnInfo(name = "duracao")
        var duracao: Int

):Serializable {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Long = 0
}