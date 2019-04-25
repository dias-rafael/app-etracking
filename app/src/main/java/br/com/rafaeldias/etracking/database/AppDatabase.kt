package br.com.rafaeldias.etracking.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import br.com.rafaeldias.etracking.dao.NotasDao
import br.com.rafaeldias.etracking.model.Notas
import java.security.AccessControlContext

@Database(entities = [Notas::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun NotasDao(): NotasDao
    companion object {
        var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase::class.java, "notas.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}