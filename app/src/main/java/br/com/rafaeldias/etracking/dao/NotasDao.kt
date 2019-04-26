package br.com.rafaeldias.etracking.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import br.com.rafaeldias.etracking.model.Notas

@Dao
interface NotasDao {

    @Query("SELECT * FROM Notas")
    fun all(): LiveData<List<Notas>>

    @Insert
    fun add(notas: Notas)

    @Update(onConflict = REPLACE)
    fun update(notas: Notas)

    @Query("DELETE FROM Notas where id = :id")
    fun del(id: Long)

}