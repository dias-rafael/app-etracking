package br.com.rafaeldias.etracking.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.widget.Toast
import br.com.rafaeldias.etracking.database.AppDatabase
import br.com.rafaeldias.etracking.model.Notas

class ListaNotasViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var notas: LiveData<List<Notas>>

    private val bd: AppDatabase = AppDatabase.getDatabase(application.applicationContext)!!

    init {
        carregarDados()
    }

    private fun carregarDados() {
        //Carregar os dados da nossa Base de dados e armazenar no LiveData
        notas = bd.NotasDao().all()
    }

}
