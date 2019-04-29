package br.com.rafaeldias.etracking.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import br.com.rafaeldias.etracking.model.Notas

@Dao
interface NotasDao {

    @Query("SELECT * FROM Notas order by numeronf")
    fun all(): LiveData<List<Notas>>

    @Insert(onConflict = REPLACE)
    fun add(notas: Notas)

    @Update
    fun upd(notas: Notas)

    @Query("DELETE FROM Notas where id = :id")
    fun del(id: Long)

    @Query("SELECT * FROM Notas where id = :id")
    fun buscaNF(id: Long): Notas
}