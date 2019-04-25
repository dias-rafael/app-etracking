package br.com.rafaeldias.etracking.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import br.com.rafaeldias.etracking.model.Notas

@Dao
interface NotasDao {

    @Query("SELECT * FROM Notas")
    fun all(): LiveData<List<Notas>>

    @Insert
    fun add(notas: Notas)

}